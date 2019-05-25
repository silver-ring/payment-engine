package com.optiva.topup.voms.db.init.initialization.usermanager;

import com.optiva.topup.voms.common.entities.usermanager.GuiUserAuthority;
import com.optiva.topup.voms.common.repositories.user.GuiUserAuthorityRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuiUserAuthorityInit implements DataInitializer {

  @Autowired
  private GuiUserAuthorityRepo guiUserAuthorityRepo;

  public void init() {

    List<GuiUserAuthority> guiUserAuthorities = new ArrayList<>();

    Field[] guiUserAuthoritiesFields = GuiUserAuthoritiesHelper.class.getFields();
    for (Field guiUserAuthoritiesField : guiUserAuthoritiesFields) {
      try {
        String value = (String) guiUserAuthoritiesField.get(null);
        GuiUserAuthority guiUserAuthority = new GuiUserAuthority();
        guiUserAuthority.setName(value);
        guiUserAuthorities.add(guiUserAuthority);
      } catch (IllegalAccessException ex) {
        throw new GuiUserAuthorityInitException(ex);
      }
    }
    guiUserAuthorityRepo.saveAll(guiUserAuthorities);
  }

}
