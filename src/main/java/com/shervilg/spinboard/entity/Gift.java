package com.shervilg.spinboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Container(containerName = "Gift", autoCreateContainer = true)
public class Gift {
  @Id
  @GeneratedValue
  private String id;

  @JsonProperty("gift_type")
  private String giftType;

  @JsonProperty("gift_name")
  private String giftName;

  @JsonProperty("coupon_code")
  private String couponCode;

  @JsonProperty("expiry_date")
  private String expiryDate;
}
