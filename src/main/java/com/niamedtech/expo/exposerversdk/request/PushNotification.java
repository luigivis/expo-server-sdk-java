package com.niamedtech.expo.exposerversdk.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class PushNotification {

  public enum Priority {
    @JsonProperty("default")
    OK,
    @JsonProperty("high")
    ERROR,
    @JsonProperty("normal")
    NORMAL;
  }

  private List<String> to;

  private Map<String, Object> data;

  private String title;

  private String subtitle;

  private String body;

  private String sound;

  private Long ttl;

  private Long expiration;

  private Priority priority;

  private Long badge;

  private String channelId;

  public PushNotification(PushNotification other) {
    this.to = other.to;
    this.title = other.title;
    this.subtitle = other.subtitle;
    this.body = other.body;
    this.sound = other.sound;
    this.ttl = other.ttl;
    this.expiration = other.expiration;
    this.priority = other.priority;
    this.badge = other.badge;
    this.channelId = other.channelId;
  }
}
