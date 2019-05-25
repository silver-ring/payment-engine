package com.optiva.topup.voms.rest.resources.changepasswordconfirmation;

import static com.optiva.topup.voms.common.types.UserConfirmationTokeType.CHANGE_PASSWORD;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.optiva.topup.voms.common.BaseWebIntegrationTest;
import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.exceptions.ConfirmationTokenNotFoundException;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

class ChangePasswordConfirmationRestITest extends BaseWebIntegrationTest {

  private static final String CHANGE_PASSWORD_CONFIRMATION = "/changePasswordConfirmation";
  private static final String CHANGE_PASSWORD_CONFIRMATION_CHECK_TOKEN =
      "/changePasswordConfirmation/checkToken";

  @MockBean
  private UserConfirmationTokenRepo userConfirmationTokenRepo;

  @MockBean
  private UserAccountRepo userAccountRepo;

  @Test
  void whenNoConfirmationTokenWhileCheckingShouldResultInException() {
    try {
      mockMvc.perform(post(CHANGE_PASSWORD_CONFIRMATION_CHECK_TOKEN)
          .contentType(MediaType.APPLICATION_JSON)
          .content(asJson(CheckChangePasswordTokenDto.builder()
              .changePasswordToken(CHANGE_PASSWORD_TOKEN).build())));
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ConfirmationTokenNotFoundException.class));
    }
  }

  @Test
  void whenConfirmationTokenWhileCheckingShouldEndsFine() throws Exception {

    Optional<UserConfirmationToken> userConfirmationToken = getUserConfirmationToken();
    when(userConfirmationTokenRepo.findByToken(CHANGE_PASSWORD_TOKEN))
        .then(invocation -> userConfirmationToken);

    MvcResult result = mockMvc.perform(post(CHANGE_PASSWORD_CONFIRMATION_CHECK_TOKEN)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(ChangePasswordConfirmationDto.builder()
            .changePasswordToken(CHANGE_PASSWORD_TOKEN).newPassword(CHANGE_PASSWORD_TOKEN).build())))
        .andExpect(status().isOk())
        .andReturn();
    assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));

    verify(userConfirmationTokenRepo, times(1)).findByToken(CHANGE_PASSWORD_TOKEN);
  }

  @Test
  void whenConfirmationTokenWhileConfirmingShouldEndsFine() throws Exception {

    Optional<UserConfirmationToken> userConfirmationToken = getUserConfirmationToken();
    when(userConfirmationTokenRepo.findByToken(CHANGE_PASSWORD_TOKEN))
        .then(invocation -> userConfirmationToken);

    Optional<UserAccount> userAccount = getUserAccount();
    when(userAccountRepo.findByEmail(ADMIN_AT_VOMS_COM)).then(invocation -> userAccount);

    when(userAccountRepo.save(userAccount.get())).thenReturn(userAccount.get());
    doNothing().when(userConfirmationTokenRepo).delete(userConfirmationToken.get());

    MvcResult result = mockMvc.perform(post(CHANGE_PASSWORD_CONFIRMATION)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(ChangePasswordConfirmationDto.builder()
            .changePasswordToken(CHANGE_PASSWORD_TOKEN).newPassword(CHANGE_PASSWORD_TOKEN).build())))
        .andExpect(status().isOk())
        .andReturn();

    assertThat(result.getResponse().getStatus(), is(HttpStatus.OK.value()));

    verify(userConfirmationTokenRepo, times(1)).findByToken(CHANGE_PASSWORD_TOKEN);
    verify(userAccountRepo, times(1)).findByEmail(ADMIN_AT_VOMS_COM);
  }

  @Test
  void whenNoConfirmationTokenWhileConfirmingShouldResultInException() {
    try {
      mockMvc.perform(post(CHANGE_PASSWORD_CONFIRMATION)
          .contentType(MediaType.APPLICATION_JSON)
          .content(asJson(ChangePasswordConfirmationDto.builder().build())));
    } catch (Exception e) {
      assertThat(e.getCause(), instanceOf(ConfirmationTokenNotFoundException.class));
    }
  }

  private Optional<UserAccount> getUserAccount() {
    return Optional.of(UserAccount.builder()
        .id(1)
        .password("OLD_PASSWORD")
        .email(ADMIN_AT_VOMS_COM)
        .build());
  }

  private Optional<UserConfirmationToken> getUserConfirmationToken() {
    return Optional.ofNullable(UserConfirmationToken.builder()
        .id(1).email(ADMIN_AT_VOMS_COM).token(CHANGE_PASSWORD_TOKEN)
        .userConfirmationTokeType(CHANGE_PASSWORD).build());
  }
}
