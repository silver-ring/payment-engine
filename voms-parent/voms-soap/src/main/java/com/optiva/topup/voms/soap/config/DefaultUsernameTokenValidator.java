package com.optiva.topup.voms.soap.config;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.UsernameTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUsernameTokenValidator extends UsernameTokenValidator {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public DefaultUsernameTokenValidator(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  protected void verifyDigestPassword(UsernameToken usernameToken,
      RequestData data) throws WSSecurityException {

    try {

      String username = usernameToken.getName();
      String password = usernameToken.getPassword();
      String passwordType = usernameToken.getPasswordType();

      WSPasswordCallback passwordCallback =
          new WSPasswordCallback(username, null, passwordType, WSPasswordCallback.USERNAME_TOKEN);
      data.getCallbackHandler().handle(new Callback[]{passwordCallback});

      String originalPassword = passwordCallback.getPassword();

      if (!passwordEncoder.matches(password, originalPassword)) {
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION);
      }

    } catch (IOException | UnsupportedCallbackException e) {
      throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_AUTHENTICATION, e);
    }

  }

}
