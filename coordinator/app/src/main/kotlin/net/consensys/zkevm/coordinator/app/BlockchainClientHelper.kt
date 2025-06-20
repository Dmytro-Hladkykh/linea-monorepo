package net.consensys.zkevm.coordinator.app

import io.vertx.core.Vertx
import io.vertx.core.http.HttpVersion
import io.vertx.ext.web.client.WebClientOptions
import linea.web3j.SmartContractErrors
import linea.web3j.transactionmanager.AsyncFriendlyTransactionManager
import net.consensys.linea.contract.l1.Web3JLineaRollupSmartContractClient
import net.consensys.linea.httprest.client.VertxHttpRestClient
import net.consensys.zkevm.coordinator.app.config.L1Config
import net.consensys.zkevm.coordinator.app.config.SignerConfig
import net.consensys.zkevm.coordinator.clients.smartcontract.LineaRollupSmartContractClient
import net.consensys.zkevm.ethereum.crypto.Web3SignerRestClient
import net.consensys.zkevm.ethereum.crypto.Web3SignerTxSignService
import net.consensys.zkevm.ethereum.signing.ECKeypairSignerAdapter
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.service.TxSignServiceImpl
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.utils.Numeric
import java.net.URI

fun createTransactionManager(
  vertx: Vertx,
  signerConfig: SignerConfig,
  client: Web3j,
): AsyncFriendlyTransactionManager {
  val transactionSignService = when (signerConfig.type) {
    SignerConfig.Type.Web3j -> {
      TxSignServiceImpl(Credentials.create(signerConfig.web3j!!.privateKey.value))
    }

    SignerConfig.Type.Web3Signer -> {
      val web3SignerConfig = signerConfig.web3signer!!
      val endpoint = URI(web3SignerConfig.endpoint)
      val webClientOptions: WebClientOptions =
        WebClientOptions()
          .setKeepAlive(web3SignerConfig.keepAlive)
          .setProtocolVersion(HttpVersion.HTTP_1_1)
          .setMaxPoolSize(web3SignerConfig.maxPoolSize.toInt())
          .setDefaultHost(endpoint.host)
          .setDefaultPort(endpoint.port)
      val httpRestClient = VertxHttpRestClient(webClientOptions, vertx)
      val signer = Web3SignerRestClient(httpRestClient, signerConfig.web3signer.publicKey)
      val signerAdapter = ECKeypairSignerAdapter(signer, Numeric.toBigInt(signerConfig.web3signer.publicKey))
      val web3SignerCredentials = Credentials.create(signerAdapter)
      Web3SignerTxSignService(web3SignerCredentials)
    }
  }

  return AsyncFriendlyTransactionManager(client, transactionSignService, -1L)
}

fun createLineaRollupContractClient(
  l1Config: L1Config,
  transactionManager: AsyncFriendlyTransactionManager,
  contractGasProvider: ContractGasProvider,
  web3jClient: Web3j,
  smartContractErrors: SmartContractErrors,
  useEthEstimateGas: Boolean,
): LineaRollupSmartContractClient {
  return Web3JLineaRollupSmartContractClient.load(
    contractAddress = l1Config.zkEvmContractAddress,
    web3j = web3jClient,
    transactionManager = transactionManager,
    contractGasProvider = contractGasProvider,
    smartContractErrors = smartContractErrors,
    useEthEstimateGas = useEthEstimateGas,
  )
}
