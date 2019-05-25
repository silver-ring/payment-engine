package com.optiva.topup.voms.rest.resources.changepasswordconfirmation;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.exceptions.ConfirmationTokenNotFoundException;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordConfirmationRest {

  private final UserConfirmationTokenRepo userConfirmationTokenRepo;
  private final UserAccountRepo userAccountRepo;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ChangePasswordConfirmationRest(UserConfirmationTokenRepo userConfirmationTokenRepo,
      UserAccountRepo userAccountRepo, PasswordEncoder passwordEncoder) {
    this.userConfirmationTokenRepo = userConfirmationTokenRepo;
    this.userAccountRepo = userAccountRepo;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/changePasswordConfirmation/checkToken")
  public void checkChangeUserPasswordToken(
      @RequestBody CheckChangePasswordTokenDto checkChangePasswordTokenDto) {
    userConfirmationTokenRepo.findByToken(checkChangePasswordTokenDto.getChangePasswordToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);
  }

  @PostMapping("/changePasswordConfirmation")
  public void changePasswordConfirmation(
      @RequestBody ChangePasswordConfirmationDto changePasswordConfirmationDto) {

    UserConfirmationToken userConfirmationToken = userConfirmationTokenRepo
        .findByToken(changePasswordConfirmationDto.getChangePasswordToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);

    UserAccount userAccount = userAccountRepo
        .findByEmail(userConfirmationToken.getEmail())
        .orElseThrow(ConfirmationTokenNotFoundException::new);

    userAccount.setPassword(passwordEncoder.encode(changePasswordConfirmationDto.getNewPassword()));

    userAccountRepo.save(userAccount);
    userConfirmationTokenRepo.delete(userConfirmationToken);
  }
}
