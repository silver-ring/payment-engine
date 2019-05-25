package com.optiva.topup.voms.db.init.initialization.usermanager;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserAccountInfo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.repositories.user.UserRoleRepo;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountInit implements DataInitializer {

  @Autowired
  private UserAccountRepo userAccountRepo;

  @Autowired
  private UserRoleRepo userRoleRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public void init() {
    UserAccountInfo userAccountInfo = new UserAccountInfo();
    userAccountInfo.setFirstName("voms");
    userAccountInfo.setLastName("admin");
    UserAccount userAccount = new UserAccount();
    userAccount.setEmail("admin@voms.com");
    userAccount.setPassword(passwordEncoder.encode("admin"));
    userAccount.setUserRoles(new HashSet<>(userRoleRepo.findAll()));
    userAccount.setUserAccountInfo(userAccountInfo);
    userAccount.setNumberOfTrials(0);
    userAccount.setUserAccountStatus(UserAccountStatus.ACTIVE);
    userAccountRepo.save(userAccount);
  }

}
