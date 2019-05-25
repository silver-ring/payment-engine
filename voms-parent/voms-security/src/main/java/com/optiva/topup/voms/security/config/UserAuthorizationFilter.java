package com.optiva.topup.voms.security.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Log4j2
public class UserAuthorizationFilter extends OncePerRequestFilter {

  private final UserAuthorizationService userAuthorizationService;

  @Autowired
  public UserAuthorizationFilter(UserAuthorizationService userAuthorizationService) {
    this.userAuthorizationService = userAuthorizationService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    filterChain.doFilter(request, response);

    String header = request.getHeader("Authorization");

    if (header == null || !header.toLowerCase().startsWith("basic ")) {
      return;
    }

    log.debug("filtering authorization header.");

    String email = extractEmail(header);
    String clientIp = request.getRemoteAddr();

    userAuthorizationService.processAuthorizationRequest(email, clientIp);
  }

  private String extractEmail(String header) {

    try {
      byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
      byte[] decoded;

      decoded = Base64.getDecoder().decode(base64Token);

      String token = new String(decoded, StandardCharsets.UTF_8);

      int delim = token.indexOf(':');

      if (delim == -1) {
        return "";
      }
      return new String[]{token.substring(0, delim), token.substring(delim + 1)}[0];

    } catch (Exception ex) {
      return "";
    }
  }

}
