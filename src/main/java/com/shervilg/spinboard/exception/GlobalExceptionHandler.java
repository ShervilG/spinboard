package com.shervilg.spinboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.shervilg.spinboard.common.constant.ExceptionConstant;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.shervilg.spinboard.dto.response.GlobalExceptionResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = RequestValidationException.class)
  public ResponseEntity<GlobalExceptionResponse> sendGlobalExceptionResponse(RequestValidationException e) {
    return new ResponseEntity<>(
        new GlobalExceptionResponse().toBuilder()
            .code(ExceptionConstant.EXCEPTION_TO_CODE_MAP.get(e.getClass()))
            .message(e.getMessage())
            .build()
        , HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(value = UnauthorizedException.class)
  public ResponseEntity<GlobalExceptionResponse> sendUnauthorizedExceptionResponse(UnauthorizedException e) {
    return new ResponseEntity<>(
        new GlobalExceptionResponse().toBuilder()
            .code(ExceptionConstant.EXCEPTION_TO_CODE_MAP.get(e.getClass()))
            .message(e.getMessage())
            .build()
        , HttpStatus.UNAUTHORIZED
    );
  }
}
