package com.shervilg.spinboard.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.common.enums.Month;
import com.shervilg.spinboard.repo.BirthdayRepository;
import com.shervilg.spinboard.service.BirthdayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;
import com.shervilg.spinboard.exception.RequestValidationException;

@Service
public class BirthdayServiceImpl implements BirthdayService {

  private static final String ALL_BDAYS_HASH_KEY = "AllBdays";

  @Value("${notification.channels}")
  private String notificationChannels;

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
