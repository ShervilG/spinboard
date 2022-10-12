package com.shervilg.spinboard.alexa.common;

import java.util.Map;

public final class AlexaSlotConstant {
  // Redis Keys
  public static final String ROOF_DOOR = "ROOF_DOOR";
  public static final String MAIN_GATE = "MAIN_GATE";
  public static final String HOUSE_GATE = "HOUSE_GATE";

  public static final Map<String, String> SLOT_VALUE_TO_REDIS_KEY_MAP = Map.of(
      "upper door", ROOF_DOOR,
      "roof door", ROOF_DOOR,
      "roof", ROOF_DOOR,
      "main gate", MAIN_GATE,
      "house gate", HOUSE_GATE
  );
}
