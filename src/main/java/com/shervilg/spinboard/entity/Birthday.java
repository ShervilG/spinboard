package com.shervilg.spinboard.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;

@Data
@Container(containerName = "Birthday", autoCreateContainer = true)
public class Birthday {

  @Id
  @GeneratedValue
  private String id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  private int date;
  private int month;
  private short priority;
}
