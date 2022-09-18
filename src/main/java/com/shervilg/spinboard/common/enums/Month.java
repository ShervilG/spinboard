package com.shervilg.spinboard.common.enums;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public enum Month {
  JAN(1),
  FEB(2),
  MAR(3),
  APR(4),
  MAY(5),
  JUN(6),
  JUL(7),
  AUG(8),
  SEPT(9),
  OCT(10),
  NOV(11),
  DEC(12);

  private int monthNumber;
}
