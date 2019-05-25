package com.optiva.topup.voms.rest.resources.usermanager.deleteuseraccount;

import static com.optiva.topup.voms.common.types.UserEmailNotificationType.DELETE_USER_ACCOUNT;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithUserEmailNotification;
import com.optiva.topup.voms.rest.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.DELETE_USER_ACCOUNT + "')")
public class DeleteUserAccountRestUserEmail implements WithUserEmailNotification {

  private final UserAccountRepo userAccountRepo;
  private final TokenUtils tokenUtils;

  @Autowired
  public DeleteUserAccountRestUserEmail(UserAccountRepo userAccountRepo, TokenUtils tokenUtils) {
    this.userAccountRepo = userAccountRepo;
    this.tokenUtils = tokenUtils;
  }

  @PostMapping("/deleteUserAccount")
  public void deleteUserAccount(@RequestBody DeleteUserAccountDto deleteUserAccountDto) {
    UserAccount userAccount = userAccountRepo.findByEmail(deleteUserAccountDto.getEmail()).get();

    userAccountRepo.delete(userAccount);
    tokenUtils.invalidateUserTokens(userAccount.getEmail());

    sendUserEmailNotificationTopic(userAccount.getEmail(), DELETE_USER_ACCOUNT);
  }

}
