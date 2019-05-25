package com.optiva.topup.voms.integration.util;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.getValueAsMap;

import com.optiva.topup.voms.common.repositories.configparameters.EmailConfigParameterRepo;
import com.optiva.topup.voms.common.types.EmailConfigParameterType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class EmailUtil {

  private final Configuration freemarkerConfig;
  private final EmailConfigParameterRepo emailConfigParameterRepo;

  @Autowired
  public EmailUtil(Configuration freemarkerConfig, EmailConfigParameterRepo emailConfigParameterRepo) {
    this.freemarkerConfig = freemarkerConfig;
    this.emailConfigParameterRepo = emailConfigParameterRepo;
  }

  public void sendEmail(NotificationEmail notificationEmail) {
    try {

      Map<String, Object> model;
      if (notificationEmail.getParameters() == null) {
        model = Collections.emptyMap();
      } else {
        model = new HashMap<>(notificationEmail.getParameters());
      }

      Template template = freemarkerConfig.getTemplate(notificationEmail.getTemplateName());
      String emailText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

      MimeMessagePreparator messagePreparator = mimeMessage -> {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(notificationEmail.getFromEmail());
        messageHelper.setTo(notificationEmail.getToEmail());
        messageHelper.setSubject(notificationEmail.getSubject());
        messageHelper.setText(emailText, true);
      };
      getEmailSender().send(messagePreparator);
    } catch (IOException | TemplateException ex) {
      throw new ReadEmailTemplateException(ex);
    }

  }

  public JavaMailSender getEmailSender() {

    Map<EmailConfigParameterType, String> emailConfigParameters = emailConfigParameterRepo.findAllAsMap();

    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
    javaMailSenderImpl.setHost(emailConfigParameters.get(EmailConfigParameterType.SMTP_HOST));
    Integer port = Integer.parseInt(emailConfigParameters.get(EmailConfigParameterType.SMTP_PORT));
    javaMailSenderImpl.setPort(port);

    String username = emailConfigParameters.get(EmailConfigParameterType.SMTP_USER_NAME);
    if (!StringUtils.isEmpty(username)) {
      javaMailSenderImpl.setUsername(username);
    }
    String password = emailConfigParameters.get(EmailConfigParameterType.SMTP_PASSWORD);
    if (!StringUtils.isEmpty(password)) {
      javaMailSenderImpl.setPassword(password);
    }

    String additionalPropertiesParameter =
        emailConfigParameters.get(EmailConfigParameterType.ADDITIONAL_PROPERTIES);

    if (StringUtils.isEmpty(additionalPropertiesParameter)) {
      return javaMailSenderImpl;
    }

    Map<String, String> additionalPropertiesMap = getValueAsMap(additionalPropertiesParameter);

    if (additionalPropertiesMap.isEmpty()) {
      return javaMailSenderImpl;
    } else {
      Properties mailProperties = javaMailSenderImpl.getJavaMailProperties();
      mailProperties.putAll(additionalPropertiesMap);
      javaMailSenderImpl.setJavaMailProperties(mailProperties);
      return javaMailSenderImpl;
    }
  }

}
