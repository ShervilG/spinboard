package com.shervilg.spinboard.service.impl;

import com.shervilg.spinboard.dto.request.CardCreationRequest;
import com.shervilg.spinboard.entity.Card;
import com.shervilg.spinboard.repo.CardRepository;
import com.shervilg.spinboard.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CardServiceImpl implements CardService {

  @Autowired
  private CardRepository cardRepository;

  @Override
  public Card createCard(CardCreationRequest cardCreationRequest) {
    Card card = new Card().toBuilder()
        .cardName(cardCreationRequest.getCardName())
        .expiryMonth(cardCreationRequest.getExpiryMonth())
        .expiryYear(cardCreationRequest.getExpiryYear())
        .build();

    return cardRepository.save(card);
  }

  @Override
  public List<Card> getAllCards() {
    return StreamSupport.stream(cardRepository.findAll().spliterator(), false).collect(Collectors.toList());
  }
}
