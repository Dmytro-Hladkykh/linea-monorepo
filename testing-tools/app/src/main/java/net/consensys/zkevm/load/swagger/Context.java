/*
 * load simulation - OpenAPI 3.0
 * describe list of requests
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package net.consensys.zkevm.load.swagger;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.consensys.zkevm.load.model.JSON;

/**
 * Context
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-03-20T14:40:46.004960500+01:00[Europe/Berlin]")
public class Context {
  public static final String SERIALIZED_NAME_URL = "url";
  @SerializedName(SERIALIZED_NAME_URL)
  private String url = "http://localhost:8545";

  public static final String SERIALIZED_NAME_NB_OF_EXECUTIONS = "nbOfExecutions";
  @SerializedName(SERIALIZED_NAME_NB_OF_EXECUTIONS)
  private Integer nbOfExecutions = 1;

  public static final String SERIALIZED_NAME_CHAIN_ID = "chainId";
  @SerializedName(SERIALIZED_NAME_CHAIN_ID)
  private Integer chainId;

  public static final String SERIALIZED_NAME_CONTRACTS = "contracts";
  @SerializedName(SERIALIZED_NAME_CONTRACTS)
  private List<CreateContract> contracts;

  public Context() {
  }

  public Context url(String url) {
    this.url = url;
    return this;
  }

   /**
   * Get url
   * @return url
  **/
  @javax.annotation.Nullable
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public Context nbOfExecutions(Integer nbOfExecutions) {
    this.nbOfExecutions = nbOfExecutions;
    return this;
  }

   /**
   * Get nbOfExecutions
   * @return nbOfExecutions
  **/
  @javax.annotation.Nullable
  public Integer getNbOfExecutions() {
    return nbOfExecutions;
  }

  public void setNbOfExecutions(Integer nbOfExecutions) {
    this.nbOfExecutions = nbOfExecutions;
  }


  public Context chainId(Integer chainId) {
    this.chainId = chainId;
    return this;
  }

   /**
   * Get chainId
   * @return chainId
  **/
  @javax.annotation.Nonnull
  public Integer getChainId() {
    return chainId;
  }

  public void setChainId(Integer chainId) {
    this.chainId = chainId;
  }


  public Context contracts(List<CreateContract> contracts) {
    this.contracts = contracts;
    return this;
  }

  public Context addContractsItem(CreateContract contractsItem) {
    if (this.contracts == null) {
      this.contracts = new ArrayList<>();
    }
    this.contracts.add(contractsItem);
    return this;
  }

   /**
   * Get contracts
   * @return contracts
  **/
  @javax.annotation.Nullable
  public List<CreateContract> getContracts() {
    return contracts;
  }

  public void setContracts(List<CreateContract> contracts) {
    this.contracts = contracts;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Context context = (Context) o;
    return Objects.equals(this.url, context.url) &&
        Objects.equals(this.nbOfExecutions, context.nbOfExecutions) &&
        Objects.equals(this.chainId, context.chainId) &&
        Objects.equals(this.contracts, context.contracts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, nbOfExecutions, chainId, contracts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Context {\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    nbOfExecutions: ").append(toIndentedString(nbOfExecutions)).append("\n");
    sb.append("    chainId: ").append(toIndentedString(chainId)).append("\n");
    sb.append("    contracts: ").append(toIndentedString(contracts)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("url");
    openapiFields.add("nbOfExecutions");
    openapiFields.add("chainId");
    openapiFields.add("contracts");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("chainId");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Context
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Context.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Context is not found in the empty JSON string", Context.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!Context.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Context` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Context.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("url") != null && !jsonObj.get("url").isJsonNull()) && !jsonObj.get("url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("url").toString()));
      }
      if (jsonObj.get("contracts") != null && !jsonObj.get("contracts").isJsonNull()) {
        JsonArray jsonArraycontracts = jsonObj.getAsJsonArray("contracts");
        if (jsonArraycontracts != null) {
          // ensure the json data is an array
          if (!jsonObj.get("contracts").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `contracts` to be an array in the JSON string but got `%s`", jsonObj.get("contracts").toString()));
          }

          // validate the optional field `contracts` (array)
          for (int i = 0; i < jsonArraycontracts.size(); i++) {
            CreateContract.validateJsonElement(jsonArraycontracts.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Context.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Context' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Context> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Context.class));

       return (TypeAdapter<T>) new TypeAdapter<Context>() {
           @Override
           public void write(JsonWriter out, Context value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Context read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Context given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Context
  * @throws IOException if the JSON string is invalid with respect to Context
  */
  public static Context fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Context.class);
  }

 /**
  * Convert an instance of Context to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
