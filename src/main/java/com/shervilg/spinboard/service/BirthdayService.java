package com.shervilg.spinboard.service;

import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;

import java.util.List;

public interface BirthdayService {
  Birthday createBirthday(BirthdayCreationRequest birthdayCreationRequest);
  List<Birthday> getAllBirthdays();
  Birthday getNearestBirthday(String... args);
}
