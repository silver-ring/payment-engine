package com.optiva.topup.voms.rest.resources.userregistrationconfirmation;

import static com.optiva.topup.voms.common.types.UserConfirmationTokenParameterType.USER_ROLES;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserAccountInfo;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.repositories.user.UserRoleRepo;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationConfirmationRest {

  private final UserConfirmationTokenRepo userConfirmationTokenRepo;
  private final UserAccountRepo userAccountRepo;
  private final PasswordEncoder passwordEncoder;
  private final UserRoleRepo userRoleRepo;

  @Autowired
  public UserRegistrationConfirmationRest(UserConfirmationTokenRepo userConfirmationTokenRepo,
      UserAccountRepo userAccountRepo, PasswordEncoder passwordEncoder, UserRoleRepo userRoleRepo) {
    this.userConfirmationTokenRepo = userConfirmationTokenRepo;
    this.userAccountRepo = userAccountRepo;
    this.passwordEncoder = passwordEncoder;
    this.userRoleRepo = userRoleRepo;
  }

  @PostMapping("/userRegistrationConfirmation/checkToken")
  public void checkActivationToken(
      @RequestBody CheckUserRegistrationConfirmationTokenDto confirmationTokenDto) {
    userConfirmationTokenRepo
        .findByToken(confirmationTokenDto.getUserRegistrationToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);
  }

  @PostMapping("/userRegistrationConfirmation")
  public void activateNewUser(@RequestBody UserRegistrationConfirmationDto confirmationDto) {

    UserConfirmationToken userConfirmationToken = userConfirmationTokenRepo
        .findByToken(confirmationDto.getUserRegistrationToken())
        .orElseThrow(ConfirmationTokenNotFoundException::new);

    if (userAccountRepo.existsByEmail(userConfirmationToken.getEmail())) {
      userConfirmationTokenRepo.delete(userConfirmationToken);
      throw new ConfirmationTokenNotFoundException();
    }

    UserAccountInfo userAccountInfo = new UserAccountInfo();
    userAccountInfo.setFirstName(confirmationDto.getFirstName());
    userAccountInfo.setLastName(confirmationDto.getLastName());

    UserAccount userAccount = new UserAccount();
    userAccount.setEmail(userConfirmationToken.getEmail());

    String encodedPassword = passwordEncoder.encode(confirmationDto.getPassword());
    userAccount.setPassword(encodedPassword);

    List<Integer> userRoleIds = (List<Integer>) userConfirmationToken.getAdditionalInfo().get(USER_ROLES);
    List<UserRole> userRoles = userRoleRepo.findAllById(userRoleIds);
    userAccount.setUserRoles(new HashSet<>(userRoles));
    userAccount.setUserAccountInfo(userAccountInfo);
    userAccount.setUserAccountStatus(UserAccountStatus.ACTIVE);
    userAccount.setNumberOfTrials(0);

    userAccountRepo.save(userAccount);
    userConfirmationTokenRepo.delete(userConfirmationToken);
  }

}
