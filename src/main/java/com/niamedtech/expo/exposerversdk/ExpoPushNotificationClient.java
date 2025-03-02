package com.niamedtech.expo.exposerversdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niamedtech.expo.exposerversdk.handler.BaseResponseHandler;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.request.ReceiptRequest;
import com.niamedtech.expo.exposerversdk.response.ReceiptResponse;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;
import com.niamedtech.expo.exposerversdk.util.ObjectMapperFactory;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * Client for synchronous communication with Expo Push Notificatio Servic. See
 * https://docs.expo.dev/push-notifications/sending-notifications/
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpoPushNotificationClient {

  private final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

  private final BaseResponseHandler<List<TicketResponse.Ticket>> sendResponseHandler =
      new BaseResponseHandler<>(TicketResponse.class);

  private final BaseResponseHandler<Map<String, ReceiptResponse.Receipt>> getReceiptHandler =
      new BaseResponseHandler<>(ReceiptResponse.class);

  @NonNull private final URI baseUri;

  @NonNull private final CloseableHttpClient httpClient;

  private final String accessToken;


  public List<TicketResponse.Ticket> sendPushNotifications(
      @NonNull List<PushNotification> notifications) throws IOException {

    final HttpPost request = createHttpPostRequest("/push/send", notifications);
    return httpClient.execute(request, sendResponseHandler);
  }

  private HttpPost createHttpPostRequest(String subpath, Object requestData)
      throws JsonProcessingException {
    final HttpPost request = new HttpPost(URI.create(baseUri.toString() + subpath));
    request.setHeader("Host", "exp.host");
    request.setHeader("accept", "application/json");
    request.setHeader("accept-encoding", "gzip, deflate");
    request.setHeader("content-type", "application/json");
    if(accessToken != null) {
      request.setHeader("Authorization", "Bearer " + accessToken);
    }

    final String json = objectMapper.writeValueAsString(requestData);
    final StringEntity stringEntity = new StringEntity(json);
    request.setEntity(stringEntity);

    return request;
  }

  public Map<String, ReceiptResponse.Receipt> getPushNotificationReceipts(@NonNull List<String> ids)
      throws IOException {

    final HttpPost request = createHttpPostRequest("/push/getReceipts", new ReceiptRequest(ids));
    return httpClient.execute(request, getReceiptHandler);
  }

  public static class Builder {

    private String baseUri = "https://exp.host/--/api/v2/";
    private String accessToken;

    private CloseableHttpClient httpClient;

    public Builder setBaseUri(@NonNull String baseUri) {
      this.baseUri = baseUri;
      return this;
    }

    public Builder setHttpClient(@NonNull CloseableHttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }


    public Builder setAccessToken(@NonNull String accessToken) {
      this.accessToken = accessToken;
      return this;
    }

    public ExpoPushNotificationClient build() {
      return new ExpoPushNotificationClient(URI.create(baseUri), httpClient, this.accessToken);
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}
