package com.shervilg.spinboard.controller;

import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.dto.BirthdayCreationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/birthday")
public class BirthdayController {

  @Autowired
  private BirthdayService birthdayService;

  @PostMapping("/create")
  public Birthday createBirthday(@RequestBody BirthdayCreationRequest birthdayCreationRequest) {
    return birthdayService.createBirthday(birthdayCreationRequest);
  }
}
