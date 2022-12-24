package com.shervilg.spinboard.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Container(containerName = "Card", autoCreateContainer = true)
public class Card {

  @Id
  @GeneratedValue
  private String id;

  private String cardName;
  private int expiryMonth;
  private int expiryYear;
}
