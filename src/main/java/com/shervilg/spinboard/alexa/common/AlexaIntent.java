package com.shervilg.spinboard.alexa.common;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum AlexaIntent {
  LIST_BDAY_INTENT("ListBirthdayIntent"),
  HELLO_WORLD_INTENT("HelloWorldIntent");

  private final String value;
}
