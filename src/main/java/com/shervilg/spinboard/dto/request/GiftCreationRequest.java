package com.shervilg.spinboard.dto.request;

import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GiftCreationRequest {
  private String name;
  private String description;
  private String giftType;
  private String couponCode;
  private String expiryDate;
}
