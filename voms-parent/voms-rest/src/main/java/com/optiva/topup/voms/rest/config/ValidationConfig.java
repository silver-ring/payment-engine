package com.optiva.topup.voms.rest.config;

import com.optiva.topup.voms.rest.resources.usermanager.useremaildomainmigration.UserEmailDomainMigrationValidator;
import com.optiva.topup.voms.rest.resources.voucherpolicy.voucherpolicyschedule.VoucherPolicyScheduleValidator;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class ValidationConfig implements RepositoryRestConfigurer {

  @Override
  public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeSave", new VoucherPolicyScheduleValidator());
    validatingListener.addValidator("beforeSave", new UserEmailDomainMigrationValidator());
    validatingListener.addValidator("beforeCreate", new UserEmailDomainMigrationValidator());
  }

}
