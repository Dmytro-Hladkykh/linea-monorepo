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

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import com.google.gson.JsonElement;

import java.util.HashSet;

import net.consensys.zkevm.load.model.JSON;

/**
 * MethodAndParameter
 */

public class MethodAndParameter {
  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  protected String type;

  public static final String SERIALIZED_NAME_NUMBER_OF_TIMES = "numberOfTimes";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_TIMES)
  private Integer numberOfTimes = 1;

  public MethodAndParameter() {
    this.type = this.getClass().getSimpleName();
  }

  public MethodAndParameter type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nonnull
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public MethodAndParameter numberOfTimes(Integer numberOfTimes) {
    this.numberOfTimes = numberOfTimes;
    return this;
  }

   /**
   * Get numberOfTimes
   * @return numberOfTimes
  **/
  @javax.annotation.Nonnull
  public Integer getNumberOfTimes() {
    return numberOfTimes;
  }

  public void setNumberOfTimes(Integer numberOfTimes) {
    this.numberOfTimes = numberOfTimes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MethodAndParameter methodAndParameter = (MethodAndParameter) o;
    return Objects.equals(this.type, methodAndParameter.type) &&
        Objects.equals(this.numberOfTimes, methodAndParameter.numberOfTimes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, numberOfTimes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MethodAndParameter {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    numberOfTimes: ").append(toIndentedString(numberOfTimes)).append("\n");
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
    openapiFields.add("type");
    openapiFields.add("numberOfTimes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("numberOfTimes");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to MethodAndParameter
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!MethodAndParameter.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in MethodAndParameter is not found in the empty JSON string", MethodAndParameter.openapiRequiredFields.toString()));
        }
      }

      String discriminatorValue = jsonElement.getAsJsonObject().get("type").getAsString();
      switch (discriminatorValue) {
        case "BatchMint":
          BatchMint.validateJsonElement(jsonElement);
          break;
        case "Mint":
          Mint.validateJsonElement(jsonElement);
          break;
        case "TransferOwnership":
          TransferOwnership.validateJsonElement(jsonElement);
          break;
        case "GenericCall":
          GenericCall.validateJsonElement(jsonElement);
          break;
        default:
          throw new IllegalArgumentException(String.format("The value of the `type` field `%s` does not match any key defined in the discriminator's mapping.", discriminatorValue));
      }
  }


 /**
  * Create an instance of MethodAndParameter given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of MethodAndParameter
  * @throws IOException if the JSON string is invalid with respect to MethodAndParameter
  */
  public static MethodAndParameter fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, MethodAndParameter.class);
  }

 /**
  * Convert an instance of MethodAndParameter to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
