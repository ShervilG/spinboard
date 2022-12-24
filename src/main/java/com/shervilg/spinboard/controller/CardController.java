package com.shervilg.spinboard.controller;

import com.shervilg.spinboard.auth.ClientAuthorization;
import com.shervilg.spinboard.dto.request.CardCreationRequest;
import com.shervilg.spinboard.entity.Card;
import com.shervilg.spinboard.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

  @Autowired
  private CardService cardService;

  @ClientAuthorization
  @PostMapping("/create")
  public Card createCard(@RequestBody CardCreationRequest cardCreationRequest) {
    return cardService.createCard(cardCreationRequest);
  }

  @ClientAuthorization
  @GetMapping("/list")
  public List<Card> getAll() {
    return cardService.getAllCards();
  }
}
