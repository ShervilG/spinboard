package com.shervilg.spinboard.dto;

import lombok.Data;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BirthdayCreationRequest {
  private String firstName;
  private String lastName;
  private int month;
  private int date;
  private short priority;
}
