package com.optiva.topup.voms.rest.resources.resetuserpasswordconfirmation;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.rest.resources.userregistrationconfirmation.ConfirmationTokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetUserPasswordConfirmationRest {

  @Autowired
  private UserConfirmationTokenRepo userConfirmationTokenRepo;

  @Autowired
  private UserAccountRepo userAccountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/resetUserPasswordConfirmation/checkToken")
  public void checkResetUserPasswordToken(
      @RequestBody CheckResetUserPasswordTokenDto checkResetUserPasswordTokenDto) {
    userConfirmationTokenRepo.findByToken(checkResetUserPasswordTokenDto.getResetUserPasswordToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);
  }

  @PostMapping("/resetUserPasswordConfirmation")
  public void resetUserPasswordConfirmation(
      @RequestBody ResetUserPasswordConfirmationDto confirmationDto) {

    UserConfirmationToken userConfirmationToken = userConfirmationTokenRepo
        .findByToken(confirmationDto.getResetUserPasswordToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);

    String encodedPassword = passwordEncoder.encode(confirmationDto.getNewPassword());

    UserAccount userAccount = userAccountRepo
        .findByEmail(userConfirmationToken.getEmail())
        .orElseThrow(ConfirmationTokenNotFoundException::new);

    userAccount.setPassword(encodedPassword);

    userAccountRepo.save(userAccount);
    userConfirmationTokenRepo.delete(userConfirmationToken);
  }

}
