package com.shervilg.spinboard.controller;

import com.shervilg.spinboard.helper.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shervilg.spinboard.dto.request.EmailRequest;

@RestController
@RequestMapping("email")
public class EmailController {

  @Autowired
  private EmailHelper emailHelper;

  @PostMapping("")
  public String sendEmail(@RequestBody EmailRequest emailRequest) {
    emailHelper.sendEmail(emailRequest);
    return "Success !";
  }
}
