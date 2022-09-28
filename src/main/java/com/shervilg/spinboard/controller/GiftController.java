package com.shervilg.spinboard.controller;

import com.shervilg.spinboard.entity.Gift;
import com.shervilg.spinboard.service.GiftService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.shervilg.spinboard.dto.request.GiftCreationRequest;

@RestController
@RequestMapping("/gift")
public class GiftController {

  @Autowired
  private GiftService giftService;

  @PostMapping("/create")
  public Gift createGift(@RequestBody GiftCreationRequest giftCreationRequest) {
    return giftService.createGift(giftCreationRequest);
  }
}
