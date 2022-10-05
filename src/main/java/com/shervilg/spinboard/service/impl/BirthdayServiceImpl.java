package com.shervilg.spinboard.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import java.util.stream.StreamSupport;
import java.util.concurrent.Executors;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.CalendarUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.common.enums.Month;
import com.shervilg.spinboard.repo.BirthdayRepository;
import com.shervilg.spinboard.service.BirthdayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.common.enums.NotificationChannel;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;
import com.shervilg.spinboard.exception.RequestValidationException;
import com.shervilg.spinboard.bot.helper.BirthdayNotificationHelper;

@Service
public class BirthdayServiceImpl implements BirthdayService {

  @Value("${notification.channels}")
  private String notificationChannels;

  @Autowired
  private BirthdayNotificationHelper birthdayNotificationHelper;

  @Autowired
  private BirthdayRepository birthdayRepository;

  @Override
  public Birthday createBirthday(BirthdayCreationRequest birthdayCreationRequest) {
    validateBirthdayCreationRequest(birthdayCreationRequest);

    return birthdayRepository.save(
        new Birthday().toBuilder()
            .date(birthdayCreationRequest.getDate())
            .month(Month.valueOf(birthdayCreationRequest.getMonth().strip()).getMonthNumber())
            .lastName(birthdayCreationRequest.getLastName().strip())
            .priority(birthdayCreationRequest.getPriority())
            .firstName(birthdayCreationRequest.getFirstName().strip())
            .build()
    );
  }

  @Override
  public List<Birthday> getAllBirthdays() {
    return StreamSupport.stream(birthdayRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  @Override
  public void checkAndSendBirthdayNotification() {
    Iterable<Birthday> birthdayList = birthdayRepository.findAll();
    if (!birthdayList.iterator().hasNext()) {
      return;
    }

    List<NotificationChannel> notificationChannelList = getEnabledNotificationChannels();
    if (notificationChannelList.size() == 0) {
      return;
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate now = LocalDate.now();
    List<Birthday> eligibleBirthdaysList = StreamSupport.stream(birthdayList.spliterator(), false)
        .filter(birthday -> {
          LocalDate birthdayDate = LocalDate.of(now.getYear(), birthday.getMonth(), birthday.getDate());

          if (birthdayDate.compareTo(now) > 0) {
            return ChronoUnit.DAYS.between(now, birthdayDate) < 30L;
          }

          return false;
        }).collect(Collectors.toList());

    if (eligibleBirthdaysList.size() == 0) {
      return;
    }

    sendParallelBirthdayNotifications(eligibleBirthdaysList, notificationChannelList);
  }

  @Override
  public Birthday getNearestBirthday() {
    List<Birthday> birthdays = getAllBirthdays();

    if (birthdays == null || birthdays.size() == 0) {
      return null;
    }

    Date dateNow = DateUtils.addMinutes(new Date(), 330);

    birthdays.sort((firstBirthday, secondBirthday) -> {
      Date firstDate = new Date(dateNow.getYear(), firstBirthday.getMonth(), firstBirthday.getDate());
      Date secondDate = new Date(dateNow.getYear(), secondBirthday.getMonth(), secondBirthday.getDate());

      if (firstDate.getTime() < dateNow.getTime()) {
        firstDate.setYear(firstDate.getYear() + 1);
      }
      if (secondDate.getTime() < dateNow.getTime()) {
        secondDate.setYear(secondDate.getYear() + 1);
      }

      return firstDate.compareTo(secondDate);
    });

    return birthdays.get(0);
  }

  private void sendParallelBirthdayNotifications(List<Birthday> birthdays, List<NotificationChannel> notificationChannelList) {
    ExecutorService notificationExecutorService = Executors.newFixedThreadPool(notificationChannelList.size());

    for (NotificationChannel notificationChannel: notificationChannelList) {

      if(NotificationChannel.DISCORD.equals(notificationChannel)) {
        notificationExecutorService.submit(
            () -> birthdayNotificationHelper.sendBirthdayNotification(birthdays)
        );
      }

    }

    notificationExecutorService.shutdown();
  }

  private List<NotificationChannel> getEnabledNotificationChannels() {
    List<NotificationChannel> result = new ArrayList<>();
    String[] channelSplit = notificationChannels.split(",");

    for (String channelString: channelSplit) {
      try {
        result.add(NotificationChannel.valueOf(channelString));
      } catch (IllegalArgumentException ignored) {}
    }

    return result;
  }

  private void validateBirthdayCreationRequest(BirthdayCreationRequest birthdayCreationRequest) {
    try {
      Month month = Month.valueOf(birthdayCreationRequest.getMonth().strip());

      if (StringUtils.isEmpty(birthdayCreationRequest.getFirstName())
          || StringUtils.isEmpty(birthdayCreationRequest.getLastName())) {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new RequestValidationException("Validations failed for the request. Kindly recheck the fields");
    }
  }
}
