package com.shervilg.spinboard.dto.request;

import lombok.Data;

@Data
public class AlexaBirthdayNotificationRequest {
  private String notification;
  private String accessCode;
  private String title;
}
