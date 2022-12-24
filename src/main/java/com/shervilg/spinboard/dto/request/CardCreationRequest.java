package com.shervilg.spinboard.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardCreationRequest {
  private String cardName;
  private int expiryMonth;
  private int expiryYear;
}
