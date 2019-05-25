package com.optiva.topup.voms.rest.resources.userprofile;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserAccountInfo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserProfileRest {

  private final UserAccountRepo userAccountRepo;

  @Autowired
  public UserProfileRest(UserAccountRepo userAccountRepo) {
    this.userAccountRepo = userAccountRepo;
  }

  @GetMapping("/userProfile")
  public ResponseEntity<UserAccountInfo> getUpdateProfile() {
    String authentication = (String) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    UserAccountInfo userAccountInfo = userAccountRepo.findByEmail(authentication).get().getUserAccountInfo();

    return new ResponseEntity<>(userAccountInfo, HttpStatus.OK);
  }

  @PatchMapping("/userProfile")
  public ResponseEntity<?> patchUpdateProfile(@RequestBody UserAccountInfo userAccountInfo) {
    String authentication = (String) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    UserAccount userAccount = userAccountRepo.findByEmail(authentication).get();
    userAccount.setUserAccountInfo(userAccountInfo);
    userAccountRepo.save(userAccount);

    return new ResponseEntity<>(userAccountInfo, HttpStatus.OK);
  }

}
