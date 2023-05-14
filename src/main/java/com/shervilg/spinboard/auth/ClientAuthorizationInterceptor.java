package com.shervilg.spinboard.auth;

import com.shervilg.spinboard.common.constant.OtherConstant;
import com.shervilg.spinboard.exception.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ClientAuthorizationInterceptor {

  @Value("${auth.token}")
  private String authToken;

  @Before(value = "@within(com.shervilg.spinboard.auth.ClientAuthentication) || " +
      "@annotation(com.shervilg.spinboard.auth.ClientAuthentication)")
  public void before(JoinPoint joinPoint) {
    validateRequest();
  }

  private void validateRequest() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();
    String authHeaderValue = request.getHeader(OtherConstant.AUTH_TOKEN_HEADER);

    if (StringUtils.isEmpty(authHeaderValue) || !authHeaderValue.equals(authToken)) {
      throw new UnauthorizedException("Auth token is not valid !");
    }
  }
}
