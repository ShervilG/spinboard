package com.shervilg.spinboard.scheduler;

import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.discord.helper.AnviDayNotificationHelper;

@Component
public class AnviDayNotificationScheduler {
  @Autowired
  private AnviDayNotificationHelper anviDayNotificationHelper;

  @Scheduled(fixedRate = 864000000L)
  public void triggerNotification() {
    Date date = new Date();
    date = DateUtils.addMinutes(date, 330);

    if (date.getDate() == 27) {
      anviDayNotificationHelper.sendAnviDayNotification();
    }
  }
}
