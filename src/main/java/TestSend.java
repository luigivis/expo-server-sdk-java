import com.luigivismara.expo.ExpoPushNotificationClient;
import com.luigivismara.expo.request.PushNotification;
import com.luigivismara.expo.response.TicketResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class TestSend {
  public static void main(String[] args) throws IOException {

    List<String> to = new ArrayList<>();
    CloseableHttpClient httpClient = HttpClients.createDefault();
    to.add("ExponentPushToken[PUSH]");

    ExpoPushNotificationClient client =
        ExpoPushNotificationClient.builder()
            .setHttpClient(httpClient)
            // .setAccessToken("TOKEN")
            .build();

    PushNotification pushNotification = new PushNotification();
    pushNotification.setTo(to);
    pushNotification.setTitle("Title");
    pushNotification.setBody("Message");

    List<PushNotification> notifications = new ArrayList<>();
    notifications.add(pushNotification);

    List<TicketResponse.Ticket> response = client.sendPushNotifications(notifications);

    for (TicketResponse.Ticket ticket : response) {
      System.out.println(ticket.getId());
      System.out.println(ticket.getStatus());
      // System.out.println(ticket.getMessage());
      // System.out.println(ticket.getDetails().getSentAt());
      // System.out.println(ticket.getDetails().getExpoPushToken());
    }
  }
}
