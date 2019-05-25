package com.optiva.topup.voms.db.init.initialization.configparameters;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.MAP;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.NUMBER;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.STRING;

import com.optiva.topup.voms.common.entities.configparameters.EmailConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.EmailConfigParameterRepo;
import com.optiva.topup.voms.common.types.EmailConfigParameterType;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailConfigParameterInit implements DataInitializer {

  @Autowired
  private EmailConfigParameterRepo emailConfigParameterRepo;

  public void init() {

    final List<EmailConfigParameter> emailConfigParameters = new ArrayList<>();

    emailConfigParameters.add(getHostConfigParameter());
    emailConfigParameters.add(getPortConfigParameter());
    emailConfigParameters.add(getUsernameConfigParameter());
    emailConfigParameters.add(getPasswordConfigParameter());
    emailConfigParameters.add(getAdditionalPropertiesConfigParameter());
    emailConfigParameters.add(getUserNotificationFromEmailConfigParameter());
    emailConfigParameters.add(getUserConfirmationUrlConfigParameter());

    emailConfigParameterRepo.saveAll(emailConfigParameters);
  }

  private EmailConfigParameter getUserConfirmationUrlConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.USER_CONFIRMATION_URL);
    configParameter.setDescription("URL use to process user confirmation");
    configParameter.setDefaultValue("http://localhost/");
    configParameter.setValue("http://localhost/");
    configParameter.setValueType(STRING);
    configParameter.setOptional(false);
    return configParameter;
  }

  private EmailConfigParameter getUserNotificationFromEmailConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter
        .setParameter(EmailConfigParameterType.USER_NOTIFICATION_FROM_EMAIL);
    configParameter.setDescription("Email address to send user notification from");
    configParameter.setDefaultValue("admin@voms.com");
    configParameter.setValue("admin@voms.com");
    configParameter.setValueType(STRING);
    configParameter.setOptional(false);
    return configParameter;
  }

  private EmailConfigParameter getAdditionalPropertiesConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.ADDITIONAL_PROPERTIES);
    configParameter
        .setDescription("comma separated key value map of additional properties");
    configParameter.setDefaultValue("");
    configParameter.setValue("");
    configParameter.setValueType(MAP);
    configParameter.setOptional(true);
    return configParameter;
  }

  private EmailConfigParameter getPasswordConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.SMTP_PASSWORD);
    configParameter.setDescription("SMTP server password");
    configParameter.setDefaultValue("");
    configParameter.setValue("");
    configParameter.setValueType(STRING);
    configParameter.setOptional(true);
    return configParameter;
  }

  private EmailConfigParameter getUsernameConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.SMTP_USER_NAME);
    configParameter.setDescription("SMTP server username");
    configParameter.setDefaultValue("");
    configParameter.setValue("");
    configParameter.setValueType(STRING);
    configParameter.setOptional(true);
    return configParameter;
  }

  private EmailConfigParameter getPortConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.SMTP_PORT);
    configParameter.setDescription("SMTP server port number");
    configParameter.setDefaultValue("1025");
    configParameter.setValue("1025");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private EmailConfigParameter getHostConfigParameter() {
    EmailConfigParameter configParameter = new EmailConfigParameter();
    configParameter.setParameter(EmailConfigParameterType.SMTP_HOST);
    configParameter.setDescription("SMTP server host address");
    configParameter.setDefaultValue("mailhog");
    configParameter.setValue("mailhog");
    configParameter.setValueType(STRING);
    configParameter.setOptional(false);
    return configParameter;
  }

}
