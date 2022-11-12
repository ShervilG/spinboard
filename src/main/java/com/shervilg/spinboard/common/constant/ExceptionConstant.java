package com.shervilg.spinboard.common.constant;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import com.shervilg.spinboard.exception.RequestValidationException;
import com.shervilg.spinboard.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;

public class ExceptionConstant {
  public static final Map<Class, Integer> EXCEPTION_TO_CODE_MAP = makeExceptionTypeToCodeMap();
  private static Map<Class, Integer> makeExceptionTypeToCodeMap() {
    return Map.of(
        RequestValidationException.class, 400,
        UnauthorizedException.class, 401
    );
  }
}
