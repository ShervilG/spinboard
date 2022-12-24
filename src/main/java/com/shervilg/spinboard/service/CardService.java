package com.shervilg.spinboard.service;

import com.shervilg.spinboard.dto.request.CardCreationRequest;
import com.shervilg.spinboard.entity.Card;

import java.util.*;

public interface CardService {
  Card createCard(CardCreationRequest cardCreationRequest);

  List<Card> getAllCards();
}
