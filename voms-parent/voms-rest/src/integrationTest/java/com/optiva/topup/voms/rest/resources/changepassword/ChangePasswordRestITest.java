package com.optiva.topup.voms.rest.resources.changepassword;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.optiva.topup.voms.common.BaseWebIntegrationTest;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.exceptions.UnauthorizedUserException;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

class ChangePasswordRestITest extends BaseWebIntegrationTest {

  private static final String CHANGE_PASSWORD_ADDRESS = "/ChangePassword";

  private static final String OLD_PASSWORD = "OLD_PASSWORD";
  private static final String OTHER_PASSWORD = "OTHER_PASSWORD";

  @MockBean
  public UserAccountRepo userAccountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void givenNoToken_whenChangePassword_thenUnauthorized() throws Exception {
    MvcResult result = mockMvc.perform(post(CHANGE_PASSWORD_ADDRESS).accept(MediaTypes.HAL_JSON_VALUE))
        .andDo(print()).andExpect(status().isUnauthorized()).andReturn();

    assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED.value())));
  }

  @Test
  void givenToken_wrongCurrentPassword_whenChangePassword_thenUnauthorized() {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthentication());

    when(userAccountRepo.findByEmail(anyString()))
        .then(invocation -> Optional.of(UserAccount.builder()
            .email(ADMIN_AT_VOMS_COM).password(OLD_PASSWORD).build()));

    try {
      MockHttpServletRequestBuilder request = post(CHANGE_PASSWORD_ADDRESS)
          .contentType(MediaType.APPLICATION_JSON)
          .content(asJson(ChangePasswordDto.builder()
              .currentPassword(OTHER_PASSWORD).build()));

      addAuthentication(request);

      mockMvc.perform(request);
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(UnauthorizedUserException.class));
    }
  }

  @Test
  void givenToken_rightCurrentPassword_whenChangePassword_thenUnauthorized() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthentication());

    when(userAccountRepo.findByEmail(anyString()))
        .then(invocation -> Optional.of(UserAccount.builder()
            .email(ADMIN_AT_VOMS_COM).password(passwordEncoder.encode(OLD_PASSWORD)).build()));

    MockHttpServletRequestBuilder request = post(CHANGE_PASSWORD_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(ChangePasswordDto.builder()
            .currentPassword(OLD_PASSWORD).build()));

    addAuthentication(request);

    assertThat(mockMvc.perform(request).andExpect(status().isOk()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.OK.value())));
  }
}
