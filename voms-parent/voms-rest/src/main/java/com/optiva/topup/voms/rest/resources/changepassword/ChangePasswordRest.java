package com.optiva.topup.voms.rest.resources.changepassword;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.exceptions.UnauthorizedUserException;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.support.WithKafkaSupport;
import com.optiva.topup.voms.common.support.WithTokenUserEmailNotification;
import com.optiva.topup.voms.common.types.UserConfirmationTokeType;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class ChangePasswordRest implements WithTokenUserEmailNotification, WithKafkaSupport {

  private final PasswordEncoder passwordEncoder;
  private final UserConfirmationTokenRepo userConfirmationTokenRepo;
  private final UserAccountRepo userAccountRepo;

  @Autowired
  public ChangePasswordRest(PasswordEncoder passwordEncoder,
      UserConfirmationTokenRepo userConfirmationTokenRepo, UserAccountRepo userAccountRepo) {
    this.passwordEncoder = passwordEncoder;
    this.userConfirmationTokenRepo = userConfirmationTokenRepo;
    this.userAccountRepo = userAccountRepo;
  }

  @PostMapping("/ChangePassword")
  public void changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

    String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (userEmail == null) {
      throw new UnauthorizedUserException();
    }

    UserAccount userAccount = userAccountRepo
        .findByEmail(userEmail).get();

    if (!passwordEncoder
        .matches(changePasswordDto.getCurrentPassword(), userAccount.getPassword())) {
      throw new UnauthorizedUserException();
    }

    UserConfirmationToken userConfirmationToken =
        userConfirmationTokenRepo.findByEmail(userEmail)
            .orElse(null);

    if (userConfirmationToken == null) {
      UserConfirmationToken newUserConfirmationToken = new UserConfirmationToken();
      newUserConfirmationToken.setEmail(userEmail);
      newUserConfirmationToken.setToken(UUID.randomUUID().toString());
      newUserConfirmationToken.setUserConfirmationTokeType(UserConfirmationTokeType.CHANGE_PASSWORD);
      userConfirmationToken = userConfirmationTokenRepo.save(newUserConfirmationToken);
    }

    sendTokenUserEmailNotificationTopic(userConfirmationToken, UserEmailNotificationType.CHANGE_PASSWORD);

  }

}
