package com.shervilg.spinboard.service.impl;

import com.shervilg.spinboard.entity.Birthday;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.repo.BirthdayRepository;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.dto.BirthdayCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class BirthdayServiceImpl implements BirthdayService {

  @Autowired
  private BirthdayRepository birthdayRepository;

  @Override
  public Birthday createBirthday(BirthdayCreationRequest birthdayCreationRequest) {
    return birthdayRepository.save(
        new Birthday().toBuilder()
            .month(birthdayCreationRequest.getMonth())
            .date(birthdayCreationRequest.getDate())
            .firstName(birthdayCreationRequest.getFirstName())
            .lastName(birthdayCreationRequest.getLastName())
            .priority(birthdayCreationRequest.getPriority())
            .build()
    );
  }
}
