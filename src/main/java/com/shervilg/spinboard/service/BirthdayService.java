package com.shervilg.spinboard.service;

import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.dto.BirthdayCreationRequest;

public interface BirthdayService {
  Birthday createBirthday(BirthdayCreationRequest birthdayCreationRequest);
}
