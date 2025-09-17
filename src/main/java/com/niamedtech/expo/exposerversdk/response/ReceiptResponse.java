package com.niamedtech.expo.exposerversdk.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Reponse including receipts for tickets. */
@Data
@EqualsAndHashCode(callSuper = false)
public final class ReceiptResponse extends BaseResponse<Map<String, ReceiptResponse.Receipt>> {

  @Data
  @EqualsAndHashCode(callSuper = false)
  public static class Receipt extends BaseResponse.GenericData {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Details {

      public enum Error {
        @JsonProperty("DeviceNotRegistered")
        DEVICE_NOT_REGISTERED,

        @JsonProperty("MessageTooBig")
        MESSAGE_TOO_BIG,

        @JsonProperty("MessageRateExceeded")
        MESSAGE_RATE_EXCEEDED,

        @JsonProperty("InvalidCredentials")
        INVALID_CREDENTIALS,

        @JsonProperty("InvalidProviderToken")
        INVALID_PROVIDERTOKEN,

        @JsonProperty("ProviderError")
        PROVIDER_ERROR,

        @JsonEnumDefaultValue
        UNKNOWN;
      }

      private Error error;
      private Integer sentAt;
      private String errorCodeEnum;
      private JsonNode additionalProperties;
      
      /**
       * Additional information returned from Firebase Cloud Messaging (FCM) for this receipt. This
       * field is populated when the notification is sent via FCM and may contain provider-specific
       * details or error information.
       */
      private JsonNode fcm;
    }

    private Status status;
    private String message;
    private Details details;
  }

  private Map<String, ReceiptResponse.Receipt> data;
}
