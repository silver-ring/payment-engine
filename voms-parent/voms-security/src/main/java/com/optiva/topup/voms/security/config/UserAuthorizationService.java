package com.optiva.topup.voms.security.config;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import com.optiva.topup.voms.common.types.UserManagementConfigParameterType;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserAuthorizationService {

  private final UserAccountRepo userAccountRepo;
  private final UserManagementConfigParameterRepo configParameterRepo;

  @Autowired
  public UserAuthorizationService(UserAccountRepo userAccountRepo,
      UserManagementConfigParameterRepo configParameterRepo) {
    this.userAccountRepo = userAccountRepo;
    this.configParameterRepo = configParameterRepo;
  }

  @Transactional
  public void processAuthorizationRequest(String email, String clientIp) {

    log.debug("Processing authorization request");

    UserAccount userAccount = userAccountRepo.findByEmail(email).orElse(null);
    if (userAccount == null) {
      return;
    }

    log.debug("User account found: {}", userAccount);

    Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

    if (existingAuth == null || !existingAuth.isAuthenticated()) {
      log.debug("User failed to authenticate.");
      userAccount.setNumberOfTrials(userAccount.getNumberOfTrials() + 1);
      lockUserAccount(userAccount);
    } else {
      userAccount.setNumberOfTrials(0);
      userAccount.setClientIpAddress(clientIp);
      userAccount.setLastLoginTime(LocalDateTime.now());
      activateUserAccount(userAccount);
    }

    userAccountRepo.save(userAccount);
  }

  private void lockUserAccount(UserAccount userAccount) {

    UserManagementConfigParameter userManagementConfigParameter = configParameterRepo
        .findByParameter(UserManagementConfigParameterType.USER_ACCOUNT_LOCK_TRAILS);

    Integer lockTrials = Integer.parseInt(userManagementConfigParameter.getValue());

    if (userAccount.getNumberOfTrials() >= lockTrials) {
      log.debug("Locking user: {} due to amount of authentication tentatives",
          userAccount);
      userAccount.setUserAccountStatus(UserAccountStatus.LOCKED);
    }
  }

  private void activateUserAccount(UserAccount userAccount) {
    userAccount.setUserAccountStatus(UserAccountStatus.ACTIVE);
  }

}
