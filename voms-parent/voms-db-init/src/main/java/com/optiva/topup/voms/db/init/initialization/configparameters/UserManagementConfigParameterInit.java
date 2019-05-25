package com.optiva.topup.voms.db.init.initialization.configparameters;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.LIST;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.NUMBER;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.ALLOWED_REGISTRATION_DOMAINS;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.LOGIN_TOKEN_EXPIRATION_DURATION;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.USER_ACCOUNT_LOCK_DURATION;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.USER_ACCOUNT_LOCK_TRAILS;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.USER_CONFIRMATION_TOKEN_EXPIRATION_DURATION;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserManagementConfigParameterInit implements DataInitializer {

  @Autowired
  private UserManagementConfigParameterRepo configParameterRepo;

  public void init() {

    final List<UserManagementConfigParameter> userManagementConfigParameters = new ArrayList<>();

    userManagementConfigParameters.add(getLoginTokenExpirationDuration());

    userManagementConfigParameters.add(getUserConfirmationTokenExpriationDuration());

    userManagementConfigParameters.add(getUserAccountLockDuration());

    userManagementConfigParameters.add(getUserAccountLockTrails());

    userManagementConfigParameters.add(getAllowedDomains());

    configParameterRepo.saveAll(userManagementConfigParameters);
  }

  private UserManagementConfigParameter getAllowedDomains() {
    UserManagementConfigParameter configParameter = new UserManagementConfigParameter();
    configParameter.setParameter(ALLOWED_REGISTRATION_DOMAINS);
    configParameter.setDescription("Comma separated list of allowed domain for user registration");
    configParameter.setDefaultValue("voms.com,test.com");
    configParameter.setValue("voms.com,test.com");
    configParameter.setValueType(LIST);
    configParameter.setOptional(false);
    return configParameter;
  }

  private UserManagementConfigParameter getUserAccountLockTrails() {
    UserManagementConfigParameter configParameter = new UserManagementConfigParameter();
    configParameter.setParameter(USER_ACCOUNT_LOCK_TRAILS);
    configParameter.setDescription("Number of trails before locking user account");
    configParameter.setDefaultValue("3");
    configParameter.setValue("3");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private UserManagementConfigParameter getUserAccountLockDuration() {
    UserManagementConfigParameter configParameter = new UserManagementConfigParameter();
    configParameter.setParameter(USER_ACCOUNT_LOCK_DURATION);
    configParameter.setDescription("Duration on minuets before unlock user account");
    configParameter.setDefaultValue("10");
    configParameter.setValue("10");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private UserManagementConfigParameter getUserConfirmationTokenExpriationDuration() {
    UserManagementConfigParameter configParameter =
        new UserManagementConfigParameter();
    configParameter.setParameter(USER_CONFIRMATION_TOKEN_EXPIRATION_DURATION);
    configParameter
        .setDescription("Duration on minuets before user confirmation token expired");
    configParameter.setDefaultValue("30");
    configParameter.setValue("30");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private UserManagementConfigParameter getLoginTokenExpirationDuration() {
    UserManagementConfigParameter configParameter = new UserManagementConfigParameter();
    configParameter.setParameter(LOGIN_TOKEN_EXPIRATION_DURATION);
    configParameter.setDescription("Duration on hours before login token expired");
    configParameter.setDefaultValue("12");
    configParameter.setValue("12");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

}
