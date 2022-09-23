package com.shervilg.spinboard.service;

import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;

public interface BirthdayService {
  Birthday createBirthday(BirthdayCreationRequest birthdayCreationRequest);
  void checkAndSendBirthdayNotification();
}
