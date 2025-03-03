import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSend {
    public static void main(String[] args) throws IOException {

        List<String> to = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        to.add("ExponentPushToken[PUSH]");

        ExpoPushNotificationClient client = ExpoPushNotificationClient
                .builder()
                .setHttpClient(httpClient)
                //.setAccessToken("TOKEN")
                .build();

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTo(to);
        pushNotification.setTitle("Title");
        pushNotification.setBody("Message");

        List<PushNotification> notifications = new ArrayList<>();
        notifications.add(pushNotification);


        List<TicketResponse.Ticket> response =  client.sendPushNotifications(notifications);

        for (TicketResponse.Ticket ticket : response) {
            System.out.println(ticket.getId());
            System.out.println(ticket.getStatus());
            //System.out.println(ticket.getMessage());
            //System.out.println(ticket.getDetails().getSentAt());
            //System.out.println(ticket.getDetails().getExpoPushToken());
        }
    }
}
