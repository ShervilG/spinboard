package com.shervilg.spinboard.service.impl;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import java.util.stream.StreamSupport;
import java.util.concurrent.Executors;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
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
import com.shervilg.spinboard.discord.helper.BirthdayNotificationHelper;

@Service
public class BirthdayServiceImpl implements BirthdayService {

  private static final String ALL_BDAYS_HASH_KEY = "AllBdays";

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
  @Cacheable("birthdays")
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
  @Cacheable("nearest-birthday")
  public Birthday getNearestBirthday(String... args) {
    List<Birthday> birthdays = getAllBirthdays();

    if (args != null && args.length > 0) {
      birthdays = birthdays.stream().filter(birthday -> birthday.getPriority() == Integer.parseInt(args[0])).collect(Collectors.toList());
    }

    if (birthdays == null || birthdays.size() == 0) {
      return null;
    }

    Map<String, Date> birthdayDateMap = new HashMap<>();
    Date dateNow = new Date();

    birthdays.forEach(birthday -> {
      Date date = new Date(dateNow.getYear(), birthday.getMonth() - 1, birthday.getDate());

      if (date.getTime() < dateNow.getTime()) {
        date.setYear(date.getYear() + 1);
      }

      birthdayDateMap.put(birthday.getId(), date);
    });

    birthdays.sort((firstBirthday, secondBirthday) -> {
      Date firstDate = birthdayDateMap.get(firstBirthday.getId());
      Date secondDate = birthdayDateMap.get(secondBirthday.getId());

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
