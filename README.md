# expo-server-sdk-java

[![license](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.luigivismara/expo-server-sdk-java.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.luigivismara/expo-server-sdk-java)
[![JitPack Release](https://jitpack.io/v/luigivis/expo-server-sdk-java.svg)](https://jitpack.io/#luigivis/expo-server-sdk-java)
[![Build](https://github.com/luigivis/expo-server-sdk-java/actions/workflows/build.yml/badge.svg)](https://github.com/luigivis/expo-server-sdk-java/actions)

---

This is a **Java implementation** of the [Expo server-side Node library](https://github.com/expo/expo-server-sdk-node)  
for working with Expo push notifications using Java.

> 🙏 Based on the original open-source work — this version is maintained and improved by **Luigi Vismara**,  
> bringing enhanced performance, structure, and compatibility with modern Java and Spring Boot environments.

---

## 📦 Installation

### Maven Central

Add **Maven Central** to your repositories:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.luigivismara:expo-server-sdk-java:1.0.0'
}
````

---

### JitPack

If you prefer **JitPack**:

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

Add the dependency (replace `Tag` with the latest version).
Check the latest tag [here](https://jitpack.io/#luigivis/expo-server-sdk-java):

```groovy
dependencies {
    implementation 'com.github.luigivis:expo-server-sdk-java:Tag'
}
```

Then sync your Gradle project.

---

## 🚀 Usage Example

```java
import com.luigivismara.expo.ExpoPushNotificationClient;
import request.com.luigivismara.expo.PushNotification;
import request.com.luigivismara.expo.PushNotification.Image;
import response.com.luigivismara.expo.TicketResponse;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.*;

public class ExpoPushExample {

    public static void main(String[] args) throws IOException {
        List<String> to = new ArrayList<>();
        to.add("ExponentPushToken[PUSH]");

        CloseableHttpClient httpClient = HttpClients.createDefault();

        ExpoPushNotificationClient client = ExpoPushNotificationClient
                .builder()
                .setHttpClient(httpClient)
                //.setAccessToken("YOUR_EXPO_ACCESS_TOKEN")
                .build();

        Map<String, Object> data = new HashMap<>();
        data.put("type", "promo");
        data.put("discount", "15%");

        // Example of notification with image and mutable content
        PushNotification pushNotification = PushNotification.builder()
                .to(to)
                .title("🎉 Big Sale!")
                .subtitle("Up to 15% off")
                .body("Tap to see our latest offers.")
                .data(data)
                .sound("default")
                .mutableContent(true) // Required for rich push notifications
                .richContent(Image.builder()
                        .image("https://example.com/images/promo-banner.png")
                        .build())
                .build();

        List<PushNotification> notifications = List.of(pushNotification);

        List<TicketResponse.Ticket> response = client.sendPushNotifications(notifications);

        for (TicketResponse.Ticket ticket : response) {
            System.out.println(ticket.getId());
            System.out.println(ticket.getStatus());
            // OK on success, ERROR on error
        }
    }
}
```

---

## 💡 Notes

* Uses **Apache HttpClient5** for secure and efficient HTTP communication.
* Fully compatible with **Java 11+** and **Spring Boot 2/3** environments.
* Supports **mutable content**, **rich content (images)**, **custom payloads**, and **batch notifications**.
* `mutableContent = true` is essential for enabling image or media-rich push notifications on iOS.

---

## 🧩 PushNotification Model

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PushNotification {

  public enum Priority {
    @JsonProperty("default") OK,
    @JsonProperty("high") ERROR,
    @JsonProperty("normal") NORMAL;
  }

  private List<String> to;
  private Map<String, Object> data;
  private String title;
  private String subtitle;
  private String body;
  private Image richContent;
  @Builder.Default private Boolean mutableContent = false;
  private String sound;
  private Long ttl;
  private Long expiration;
  private Priority priority;
  private Long badge;
  private String channelId;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Image {
      private String image;
  }
}
```

---

## ❤️ Acknowledgment

All original groundwork and inspiration come from the [Expo Node SDK](https://github.com/expo/expo-server-sdk-node).
This Java version is **maintained and enhanced by [Luigi Vismara](https://github.com/luigivismara)**
to bring improved structure, documentation, and support for rich push content.