package com.optiva.topup.voms.db.init.initialization.usermanager;

import com.optiva.topup.voms.common.entities.usermanager.ApiUserAuthority;
import com.optiva.topup.voms.common.repositories.user.ApiUserAuthorityRepo;
import com.optiva.topup.voms.common.security.ApiUserAuthoritiesHelper;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiUserAuthorityInit implements DataInitializer {

  @Autowired
  private ApiUserAuthorityRepo apiUserAuthorityRepo;

  public void init() {

    List<ApiUserAuthority> apiUserAuthorities = new ArrayList<>();

    Field[] apiUserAuthoritiesFields = ApiUserAuthoritiesHelper.class.getFields();
    for (Field apiUserAuthoritiesField : apiUserAuthoritiesFields) {
      try {
        String value = (String) apiUserAuthoritiesField.get(null);
        ApiUserAuthority apiUserAuthority = new ApiUserAuthority();
        apiUserAuthority.setName(value);
        apiUserAuthorities.add(apiUserAuthority);
      } catch (IllegalAccessException ex) {
        throw new ApiUserAuthorityInitException(ex);
      }
    }
    apiUserAuthorityRepo.saveAll(apiUserAuthorities);
  }

}
