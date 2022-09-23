package com.shervilg.spinboard.scheduler;

import org.springframework.stereotype.Component;
import com.shervilg.spinboard.service.BirthdayService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class BirthdayNotificationScheduler {

  @Autowired
  private BirthdayService birthdayService;

  @Scheduled(fixedRate = 604800000L)
  public void sendBirthdayNotification() {
    birthdayService.checkAndSendBirthdayNotification();
  }
}
