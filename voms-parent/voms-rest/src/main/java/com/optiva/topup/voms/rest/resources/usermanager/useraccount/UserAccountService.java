package com.optiva.topup.voms.rest.resources.usermanager.useraccount;

import static java.util.stream.Collectors.toList;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.exceptions.UserAccountNotFoundException;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.repositories.user.UserRoleRepo;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {

  private final UserAccountRepo userAccountRepo;
  private final UserRoleRepo userRoleRepo;

  @Autowired
  public UserAccountService(UserAccountRepo userAccountRepo, UserRoleRepo userRoleRepo) {
    this.userAccountRepo = userAccountRepo;
    this.userRoleRepo = userRoleRepo;
  }

  public List<UserAccount> findByEmail(String email) {
    UserAccount userAccount =
        userAccountRepo.findByEmail(email).orElseThrow(UserAccountNotFoundException::new);
    List<UserAccount> userAccounts = new ArrayList<>();
    userAccounts.add(userAccount);
    return userAccounts;
  }

  @Transactional
  public List<UserRole> findUserRoles(Integer userAccountId) {
    List<Integer> userRoleIds = userAccountRepo
        .findById(userAccountId)
        .orElseThrow(UserAccountNotFoundException::new)
        .getUserRoles()
        .stream()
        .map(UserRole::getId)
        .collect(toList());
    return userRoleRepo.findAllById(userRoleIds);
  }

}
