package com.shervilg.spinboard.controller;

import com.shervilg.spinboard.auth.ClientAuthorization;
import com.shervilg.spinboard.entity.Birthday;
import com.shervilg.spinboard.service.BirthdayService;
import com.shervilg.spinboard.dto.request.BirthdayCreationRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/birthday")
public class BirthdayController {

  @Autowired
  private BirthdayService birthdayService;

  @ClientAuthorization
  @PostMapping("/create")
  public Birthday createBirthday(@RequestBody BirthdayCreationRequest birthdayCreationRequest) {
    return birthdayService.createBirthday(birthdayCreationRequest);
  }

  @ClientAuthorization
  @GetMapping("/list")
  public List<Birthday> listBirthdays() {
    return birthdayService.getAllBirthdays();
  }
}
