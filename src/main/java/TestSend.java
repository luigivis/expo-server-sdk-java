import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestSend {
    public static void main(String[] args) throws IOException {

        List<String> to = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        to.add("ExponentPushToken[YOUR_TOKEN]");

        ExpoPushNotificationClient client = ExpoPushNotificationClient
                .builder()
                .setHttpClient(httpClient)
                .setAccessToken("YOU_TOKEN")
                .build();

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTo(to);
        pushNotification.setTitle("Title");
        pushNotification.setBody("Body Message");

        List<PushNotification> notifications = new ArrayList<>();
        notifications.add(pushNotification);

        client.sendPushNotifications(notifications);

    }
}
