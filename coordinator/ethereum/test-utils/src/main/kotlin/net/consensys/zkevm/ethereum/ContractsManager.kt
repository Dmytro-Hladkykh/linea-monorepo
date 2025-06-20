package net.consensys.zkevm.ethereum

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addFileSource
import linea.contract.l1.LineaContractVersion
import linea.kotlin.gwei
import linea.web3j.SmartContractErrors
import linea.web3j.gas.StaticGasProvider
import linea.web3j.transactionmanager.AsyncFriendlyTransactionManager
import net.consensys.linea.contract.l1.Web3JLineaRollupSmartContractClient
import net.consensys.linea.testing.filesystem.findPathTo
import net.consensys.zkevm.coordinator.clients.smartcontract.LineaRollupSmartContractClient
import org.slf4j.LoggerFactory
import org.web3j.tx.gas.ContractEIP1559GasProvider
import tech.pegasys.teku.infrastructure.async.SafeFuture

data class LineaRollupDeploymentResult(
  val contractAddress: String,
  val contractDeploymentAccount: Account,
  val contractDeploymentBlockNumber: ULong,
  val rollupOperators: List<AccountTransactionManager>,
  val rollupOperatorClient: LineaRollupSmartContractClient,
) {
  val rollupOperator: AccountTransactionManager
    get() = rollupOperators.first()
}

data class L2MessageServiceDeploymentResult(
  val contractAddress: String,
  val contractDeploymentBlockNumber: ULong,
  val anchorerOperator: AccountTransactionManager,
)

data class ContactsDeploymentResult(
  val lineaRollup: LineaRollupDeploymentResult,
  val l2MessageService: L2MessageServiceDeploymentResult,
)

interface ContractsManager {
  /**
   * Deploys a linea rollup contract with specified number of operators.
   * Operator accounts are generated on the fly and funded from whale account in genesis file.
   */
  fun deployLineaRollup(
    numberOfOperators: Int = 1,
    contractVersion: LineaContractVersion,
  ): SafeFuture<LineaRollupDeploymentResult>

  fun deployL2MessageService(): SafeFuture<L2MessageServiceDeploymentResult>

  fun deployRollupAndL2MessageService(
    dataCompressionAndProofAggregationMigrationBlock: ULong = 1000UL,
    numberOfOperators: Int = 1,
    l1ContractVersion: LineaContractVersion = LineaContractVersion.V6,
  ): SafeFuture<ContactsDeploymentResult>

  fun connectToLineaRollupContract(
    contractAddress: String,
    transactionManager: AsyncFriendlyTransactionManager,
    gasProvider: ContractEIP1559GasProvider = StaticGasProvider(
      L1AccountManager.chainId,
      maxFeePerGas = 55UL.gwei,
      maxPriorityFeePerGas = 50UL.gwei,
      maxFeePerBlobGas = 1_000UL.gwei,
      gasLimit = 1_000_000uL,
    ),
    smartContractErrors: SmartContractErrors? = null,
  ): LineaRollupSmartContractClient

  companion object {
    fun get(): ContractsManager = MakeFileDelegatedContractsManager
  }
}

object MakeFileDelegatedContractsManager : ContractsManager {
  val log = LoggerFactory.getLogger(MakeFileDelegatedContractsManager::class.java)
  val lineaRollupContractErrors = findPathTo("config")!!
    .resolve("common/smart-contract-errors.toml")
    .let { filePath ->
      data class ErrorsFile(val smartContractErrors: Map<String, String>)
      ConfigLoaderBuilder.default()
        .addFileSource(filePath.toAbsolutePath().toString())
        .build()
        .loadConfigOrThrow<ErrorsFile>()
        .smartContractErrors
    }

  override fun deployLineaRollup(
    numberOfOperators: Int,
    contractVersion: LineaContractVersion,
  ): SafeFuture<LineaRollupDeploymentResult> {
    val newAccounts = L1AccountManager.generateAccounts(numberOfOperators)
    val contractDeploymentAccount = newAccounts.first()
    val operatorsAccounts = newAccounts.drop(1)
    log.debug(
      "going deploy LineaRollup: deployerAccount={} rollupOperators={}",
      contractDeploymentAccount.address,
      operatorsAccounts.map { it.address },
    )
    val future = makeDeployLineaRollup(
      deploymentPrivateKey = contractDeploymentAccount.privateKey,
      operatorsAddresses = operatorsAccounts.map { it.address },
      contractVersion = contractVersion,
    )
      .thenApply { deploymentResult ->
        log.debug(
          "LineaRollup deployed: address={} blockNumber={} deployerAccount={} " +
            "rollupOperators={}",
          deploymentResult.address,
          deploymentResult.blockNumber,
          contractDeploymentAccount.address,
          operatorsAccounts.map { it.address },
        )
        val accountsTxManagers = operatorsAccounts.map {
          AccountTransactionManager(it, L1AccountManager.getTransactionManager(it))
        }

        val rollupOperatorClient = connectToLineaRollupContract(
          deploymentResult.address,
          accountsTxManagers.first().txManager,
          smartContractErrors = lineaRollupContractErrors,
        )
        LineaRollupDeploymentResult(
          contractAddress = deploymentResult.address,
          contractDeploymentAccount = contractDeploymentAccount,
          contractDeploymentBlockNumber = deploymentResult.blockNumber.toULong(),
          rollupOperators = accountsTxManagers,
          rollupOperatorClient = rollupOperatorClient,
        )
      }
    return future
  }

  override fun deployL2MessageService(): SafeFuture<L2MessageServiceDeploymentResult> {
    val (deployerAccount, anchorerAccount) = L2AccountManager.generateAccounts(2)
    return makeDeployL2MessageService(
      deploymentPrivateKey = deployerAccount.privateKey,
      anchorOperatorAddresses = anchorerAccount.address,
    )
      .thenApply {
        L2MessageServiceDeploymentResult(
          contractAddress = it.address,
          contractDeploymentBlockNumber = it.blockNumber.toULong(),
          anchorerOperator = AccountTransactionManager(
            account = anchorerAccount,
            txManager = L2AccountManager.getTransactionManager(anchorerAccount),
          ),
        )
      }
  }

  override fun deployRollupAndL2MessageService(
    dataCompressionAndProofAggregationMigrationBlock: ULong,
    numberOfOperators: Int,
    l1ContractVersion: LineaContractVersion,
  ): SafeFuture<ContactsDeploymentResult> {
    return deployLineaRollup(numberOfOperators, l1ContractVersion)
      .thenCombine(deployL2MessageService()) { lineaRollupDeploymentResult, l2MessageServiceDeploymentResult ->
        ContactsDeploymentResult(
          lineaRollup = lineaRollupDeploymentResult,
          l2MessageService = l2MessageServiceDeploymentResult,
        )
      }
  }

  override fun connectToLineaRollupContract(
    contractAddress: String,
    transactionManager: AsyncFriendlyTransactionManager,
    gasProvider: ContractEIP1559GasProvider,
    smartContractErrors: SmartContractErrors?,
  ): LineaRollupSmartContractClient {
    return Web3JLineaRollupSmartContractClient.load(
      contractAddress,
      Web3jClientManager.l1Client,
      transactionManager,
      gasProvider,
      smartContractErrors ?: lineaRollupContractErrors,
    )
  }
}

fun main() {
  data class SmartContractErrors(val smartContractErrors: Map<String, String>)

  val lineaRollupContractErrors = findPathTo("config")!!
    .resolve("common/smart-contract-errors.toml")
    .let { filePath ->
      ConfigLoaderBuilder.default()
        .addFileSource(filePath.toAbsolutePath().toString())
        .build()
        .loadConfigOrThrow<SmartContractErrors>()
    }
  println(lineaRollupContractErrors)
}
