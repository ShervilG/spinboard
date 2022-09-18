package com.shervilg.spinboard.dto.request;

import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BirthdayCreationRequest {
  private int date;
  private String month;
  private short priority;
  private String lastName;
  private String firstName;
}
