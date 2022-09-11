package com.shervilg.spinboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

  @RequestMapping("/ping")
  public String getHealth() {
    return "Pong !";
  }
}
