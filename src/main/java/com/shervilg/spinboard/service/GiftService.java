package com.shervilg.spinboard.service;

import com.shervilg.spinboard.dto.request.GiftCreationRequest;
import com.shervilg.spinboard.entity.Gift;

public interface GiftService {
  Gift createGift(GiftCreationRequest giftCreationRequest);
}
