package com.shervilg.spinboard.service.impl;

import com.shervilg.spinboard.common.enums.GiftType;
import com.shervilg.spinboard.entity.Gift;
import com.shervilg.spinboard.exception.RequestValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.shervilg.spinboard.repo.GiftRepository;
import com.shervilg.spinboard.service.GiftService;
import com.shervilg.spinboard.dto.request.GiftCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class GiftServiceImpl implements GiftService {

  @Autowired
  private GiftRepository giftRepository;

  @Override
  public Gift createGift(GiftCreationRequest giftCreationRequest) {
    Gift gift = new Gift().toBuilder()
        .giftName(giftCreationRequest.getName().trim())
        .giftType(GiftType.valueOf(giftCreationRequest.getGiftType()).name())
        .description(giftCreationRequest.getDescription().trim())
        .couponCode(giftCreationRequest.getCouponCode() != null ? giftCreationRequest.getCouponCode().trim() : null)
        .expiryDate(giftCreationRequest.getExpiryDate() != null ? giftCreationRequest.getExpiryDate().trim() : null)
        .build();

    return giftRepository.save(gift);
  }

  private void validateGiftCreationRequest(GiftCreationRequest giftCreationRequest) {
    try {
      GiftType giftType = GiftType.valueOf(giftCreationRequest.getGiftType());

      if (
          StringUtils.isEmpty(giftCreationRequest.getName().trim()) ||
              StringUtils.isEmpty(giftCreationRequest.getDescription().trim()) ||
              (giftType.equals(GiftType.COUPON) && StringUtils.isEmpty(giftCreationRequest.getCouponCode().trim())) ||
              (!StringUtils.isEmpty(giftCreationRequest.getCouponCode().trim()) &&
                  StringUtils.isEmpty(giftCreationRequest.getExpiryDate().trim()))
      ) {
        throw new Exception();
      }
    } catch (Exception e) {
      throw new RequestValidationException("Please recheck the fields !");
    }
  }
}
