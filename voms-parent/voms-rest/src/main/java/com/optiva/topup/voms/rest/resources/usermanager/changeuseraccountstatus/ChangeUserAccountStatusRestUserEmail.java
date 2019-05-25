package com.optiva.topup.voms.rest.resources.usermanager.changeuseraccountstatus;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.exceptions.UserAccountNotFoundException;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithParametrizedUserEmailNotification;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import com.optiva.topup.voms.rest.utils.TokenUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.CHANGE_USER_ACCOUNT_STATUS + "')")
public class ChangeUserAccountStatusRestUserEmail implements WithParametrizedUserEmailNotification {

  private final UserAccountRepo userAccountRepo;
  private final TokenUtils tokenUtils;

  @Autowired
  public ChangeUserAccountStatusRestUserEmail(UserAccountRepo userAccountRepo, TokenUtils tokenUtils) {
    this.userAccountRepo = userAccountRepo;
    this.tokenUtils = tokenUtils;
  }

  @PostMapping("/changeUserAccountStatus")
  public void changeUserAccountStatus(@RequestBody ChangeUserAccountStatusDto changeUserAccountStatusDto) {

    UserAccount userAccount = userAccountRepo
        .findByEmail(changeUserAccountStatusDto.getEmail())
        .orElseThrow(UserAccountNotFoundException::new);
    userAccount.setUserAccountStatus(changeUserAccountStatusDto.getNewUserAccountStatus());

    tokenUtils.invalidateUserTokens(changeUserAccountStatusDto.getEmail());
    userAccountRepo.save(userAccount);

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("newUserAccountStatus", changeUserAccountStatusDto.getNewUserAccountStatus().name());

    sendUserEmailNotificationTopic(changeUserAccountStatusDto.getEmail(),
        UserEmailNotificationType.CHANGE_USER_ACCOUNT_STATUS, parameters);
  }

}
