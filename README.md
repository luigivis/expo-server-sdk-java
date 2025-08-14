## expo-server-sdk-java
This is a java implementation of the [node server-side library](https://github.com/expo/expo-server-sdk-node) for working with expo using Java.

## Installation

### Maven Central [![Maven Central](https://img.shields.io/maven-central/v/io.github.hlspablo/expo-server-sdk-java.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.hlspablo/expo-server-sdk-java)
Add **Maven Central** to your repositories (usually already included by default in Gradle projects):

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.hlspablo:expo-server-sdk-java:3.1.5'
}
```

### Jitpack
Add the JitPack repository to your build file
```groovy
repositories {
				mavenCentral()
				maven { url 'https://jitpack.io' }
		}
```
Add the dependency and replace `Tag` with the latest tag.
check the latest tag [here](https://jitpack.io/private#hlspablo/expo-server-sdk-java)
```groovy
dependencies {
				implementation 'com.github.hlspablo:expo-server-sdk-java:Tag'
}
```
Sync you gradle

## Usage
```java
import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

				// Play Sound - iOS Only ( Android uses Channels to configure Sounds )
				// pass the sound name ( the sound must be already available on the project )
				// Check: https://docs.expo.dev/versions/latest/sdk/notifications/#configurable-properties
				// and https://docs.expo.dev/versions/latest/sdk/notifications/#set-custom-notification-sounds
				pushNotification.setSound("sound.wav");

				List<PushNotification> notifications = new ArrayList<>();
				notifications.add(pushNotification);


				List<TicketResponse.Ticket> response =  client.sendPushNotifications(notifications);

				for (TicketResponse.Ticket ticket : response) {
						System.out.println(ticket.getId());
						System.out.println(ticket.getStatus());
						// OK on success, ERROR on error
						// use import com.niamedtech.expo.exposerversdk.response.Status;

						// getDetails is only available on Error
						//System.out.println(ticket.getMessage());
						//System.out.println(ticket.getDetails().getSentAt());
						//System.out.println(ticket.getDetails().getExpoPushToken());
				}
```

## All hard work was done by:
- [expo-server-sdk-node](https://github.com/nia-medtech/expo-server-sdk-java)
