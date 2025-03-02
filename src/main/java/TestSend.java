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
        to.add("ExponentPushToken[SvGo_CI-bb-9R7T4ls2gvH]");

        ExpoPushNotificationClient client = ExpoPushNotificationClient
                .builder()
                .setHttpClient(httpClient)
                .setAccessToken("cKW0GpijtpwdryzZIkQd6RJMHa8-cQZIyDmvoXma")
                .build();

        PushNotification pushNotification = new PushNotification();
        pushNotification.setTo(to);
        pushNotification.setTitle("MangaRosa");
        pushNotification.setBody("Corpo3");

        List<PushNotification> notifications = new ArrayList<>();
        notifications.add(pushNotification);

        client.sendPushNotifications(notifications);

    }
}
