package com.optiva.topup.voms.db.init.initialization.usermanager;

import static com.optiva.topup.voms.common.types.Status.ACTIVE;

import com.optiva.topup.voms.common.entities.usermanager.ApiUserAuthority;
import com.optiva.topup.voms.common.entities.usermanager.GuiUserAuthority;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.repositories.user.ApiUserAuthorityRepo;
import com.optiva.topup.voms.common.repositories.user.GuiUserAuthorityRepo;
import com.optiva.topup.voms.common.repositories.user.UserRoleRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRoleInit implements DataInitializer {

  @Autowired
  private UserRoleRepo userRoleRepo;

  @Autowired
  private ApiUserAuthorityRepo apiUserAuthorityRepo;

  @Autowired
  private GuiUserAuthorityRepo guiUserAuthorityRepo;

  public void init() {
    List<GuiUserAuthority> guiUserAuthorities = guiUserAuthorityRepo.findAll();
    List<ApiUserAuthority> apiUserAuthorities = apiUserAuthorityRepo.findAll();

    UserRole superAdmin = new UserRole();
    superAdmin.setName("super admin");
    superAdmin.setGuiUserAuthorities(new HashSet<>(guiUserAuthorities));
    superAdmin.setApiUserAuthorities(new HashSet<>(apiUserAuthorities));
    superAdmin.setStatus(ACTIVE);
    userRoleRepo.save(superAdmin);
  }

}
