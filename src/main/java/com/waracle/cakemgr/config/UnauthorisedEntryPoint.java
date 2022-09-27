package com.waracle.cakemgr.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Custom endpoint to return when user makes unauthorised request */
public class UnauthorisedEntryPoint implements AuthenticationEntryPoint {
  @Override
  public final void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().print("You are not authorised.");
  }
}
