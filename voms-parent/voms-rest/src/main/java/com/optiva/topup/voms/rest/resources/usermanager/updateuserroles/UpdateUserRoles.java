package com.optiva.topup.voms.rest.resources.usermanager.updateuserroles;

import static com.optiva.topup.voms.common.types.UserEmailNotificationType.UPDATE_USER_ROLES;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.repositories.user.UserRoleRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithUserEmailNotification;
import com.optiva.topup.voms.rest.utils.RestUtils;
import com.optiva.topup.voms.rest.utils.TokenUtils;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.UPDATE_USER_ROLES + "')")
public class UpdateUserRoles implements WithUserEmailNotification {

  private final UserAccountRepo userAccountRepo;
  private final UserRoleRepo userRoleRepo;
  private final TokenUtils tokenUtils;
  private final RestUtils restUtils;

  @Autowired
  public UpdateUserRoles(UserAccountRepo userAccountRepo, UserRoleRepo userRoleRepo, TokenUtils tokenUtils,
      RestUtils restUtils) {
    this.userAccountRepo = userAccountRepo;
    this.userRoleRepo = userRoleRepo;
    this.tokenUtils = tokenUtils;
    this.restUtils = restUtils;
  }

  @PutMapping("/updateUserRoles")
  public void updateUserRoles(@RequestBody UpdateUserRolesDto updateUserRolesDto) {

    List<Integer> userRolesIds = restUtils.extractIdsFromLinks(updateUserRolesDto.getUserRoles());

    List<UserRole> userRoles = userRoleRepo.findAllById(userRolesIds);

    UserAccount queriedUserAccount = userAccountRepo.findByEmail(updateUserRolesDto.getEmail()).get();
    queriedUserAccount.setUserRoles(new HashSet<>(userRoles));

    userAccountRepo.save(queriedUserAccount);
    tokenUtils.invalidateUserTokens(queriedUserAccount.getEmail());

    sendUserEmailNotificationTopic(queriedUserAccount.getEmail(), UPDATE_USER_ROLES);

  }

}
