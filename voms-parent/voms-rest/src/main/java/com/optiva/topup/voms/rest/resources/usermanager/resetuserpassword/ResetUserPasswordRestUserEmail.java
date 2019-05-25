package com.optiva.topup.voms.rest.resources.usermanager.resetuserpassword;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.exceptions.UserAccountNotFoundException;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithKafkaSupport;
import com.optiva.topup.voms.common.support.WithTokenUserEmailNotification;
import com.optiva.topup.voms.common.types.UserConfirmationTokeType;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.RESET_USER_PASSWORD + "')")
public class ResetUserPasswordRestUserEmail implements WithKafkaSupport, WithTokenUserEmailNotification {

  private final UserConfirmationTokenRepo userConfirmationTokenRepo;
  private final UserAccountRepo userAccountRepo;

  @Autowired
  public ResetUserPasswordRestUserEmail(UserConfirmationTokenRepo userConfirmationTokenRepo,
      UserAccountRepo userAccountRepo) {
    this.userConfirmationTokenRepo = userConfirmationTokenRepo;
    this.userAccountRepo = userAccountRepo;
  }

  @PostMapping("/resetUserPassword")
  public void resetUserPassword(@RequestBody ResetUserPasswordDto resetUserPasswordDto) {

    if (!userAccountRepo.existsByEmail(resetUserPasswordDto.getEmail())) {
      throw new UserAccountNotFoundException();
    }

    UserConfirmationToken userConfirmationToken = userConfirmationTokenRepo
        .findByEmail(resetUserPasswordDto.getEmail())
        .orElse(null);

    if (userConfirmationToken == null) {
      UserConfirmationToken newUserConfirmationToken = new UserConfirmationToken();
      newUserConfirmationToken.setToken(UUID.randomUUID().toString());
      newUserConfirmationToken.setEmail(resetUserPasswordDto.getEmail());
      newUserConfirmationToken.setUserConfirmationTokeType(UserConfirmationTokeType.RESET_USER_PASSWORD);
      userConfirmationToken = userConfirmationTokenRepo.save(newUserConfirmationToken);
    }

    sendTokenUserEmailNotificationTopic(userConfirmationToken, UserEmailNotificationType.RESET_USER_PASSWORD);

  }

}
