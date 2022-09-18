package com.shervilg.spinboard.common.constant;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import com.shervilg.spinboard.exception.RequestValidationException;
import org.springframework.http.HttpStatus;

public class ExceptionConstant {
  public static final Map<Class, Integer> EXCEPTION_TO_CODE_MAP = makeExceptionTypeToCodeMap();
  private static Map<Class, Integer> makeExceptionTypeToCodeMap() {
    Map<Class, Integer> map = new HashMap<>();
    map.put(RequestValidationException.class, 400);

    return Collections.unmodifiableMap(map);
  }
}
