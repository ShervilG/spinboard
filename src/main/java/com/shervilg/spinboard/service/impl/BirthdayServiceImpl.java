package com.shervilg.spinboard.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.common.enums.Month;
import com.shervilg.spinboard.repo.BirthdayRepository;
import com.shervilg.spinboard.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;
import com.shervilg.spinboard.exception.RequestValidationException;

@Service
public class BirthdayServiceImpl implements BirthdayService {

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
