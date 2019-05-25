package com.optiva.topup.voms.rest.resources.usermanager.useremaildomainmigration;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.getValueAsList;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.ALLOWED_REGISTRATION_DOMAINS;

import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.rest.exceptions.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserEmailDomainMigrationValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return UserEmailDomainMigration.class.equals(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {

    UserManagementConfigParameterRepo configParameterRepo = ApplicationContextUtil
        .getApplicationContext()
        .getBean(UserManagementConfigParameterRepo.class);

    String allowedRegistrationDomains = configParameterRepo
        .findByParameter(ALLOWED_REGISTRATION_DOMAINS)
        .getValue();

    String migrateToDomain = ((UserEmailDomainMigration) obj).getMigrateToDomain();

    Boolean isAcceptedDomain = getValueAsList(allowedRegistrationDomains)
        .stream()
        .anyMatch(domain -> StringUtils.equals(domain, migrateToDomain));

    if (!isAcceptedDomain) {
      throw new ValidationException("Migrate to domain is not allowed");
    }
  }

}
