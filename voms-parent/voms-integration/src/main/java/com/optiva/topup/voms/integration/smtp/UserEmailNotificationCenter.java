package com.optiva.topup.voms.integration.smtp;

import static com.optiva.topup.voms.common.support.WithParametrizedUserEmailNotification.SEND_USER_NOTIFICATION_EMAIL_TOPIC;
import static com.optiva.topup.voms.common.types.EmailConfigParameterType.USER_CONFIRMATION_URL;
import static com.optiva.topup.voms.common.types.EmailConfigParameterType.USER_NOTIFICATION_FROM_EMAIL;

import com.optiva.topup.voms.common.document.UserNotificationEmailHistory;
import com.optiva.topup.voms.common.messages.UserEmailNotificationMessage;
import com.optiva.topup.voms.common.repositories.configparameters.EmailConfigParameterRepo;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import com.optiva.topup.voms.common.types.UserNotificationEmailStatus;
import com.optiva.topup.voms.integration.util.EmailUtil;
import com.optiva.topup.voms.integration.util.NotificationEmail;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.common.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserEmailNotificationCenter implements WithDocumentTopicsSupport {

  private final EmailUtil emailUtil;
  private final EmailConfigParameterRepo emailConfigParameterRepo;

  @Autowired
  public UserEmailNotificationCenter(EmailUtil emailUtil, EmailConfigParameterRepo emailConfigParameterRepo) {
    this.emailUtil = emailUtil;
    this.emailConfigParameterRepo = emailConfigParameterRepo;
  }

  @KafkaListener(topics = SEND_USER_NOTIFICATION_EMAIL_TOPIC)
  public void sendUserNotificationEmail(UserEmailNotificationMessage userEmailNotificationMessage) {

    UserNotificationEmailHistory userNotificationEmailHistory = new UserNotificationEmailHistory();
    try {
      UserEmailNotificationType
          userEmailNotificationType = userEmailNotificationMessage.getUserEmailNotificationType();

      Map<String, Object> parameters = userEmailNotificationMessage.getParameters();

      addConfirmationLinkParameter(userEmailNotificationType, parameters);

      String fromEmail = emailConfigParameterRepo
          .findByParameter(USER_NOTIFICATION_FROM_EMAIL)
          .getValue();

      String toEmail = userEmailNotificationMessage.getEmail();
      userNotificationEmailHistory.setSendFromEmail(fromEmail);

      userNotificationEmailHistory.setSendToEmail(toEmail);

      String templateName = userEmailNotificationType.getTemplate();
      userNotificationEmailHistory.setTemplateName(templateName);

      userNotificationEmailHistory.setParameters(parameters);
      userNotificationEmailHistory.setUserEmailNotificationType(userEmailNotificationType);

      String subject = userEmailNotificationType.getSubject();
      userNotificationEmailHistory.setSubject(subject);
      emailUtil.sendEmail(new NotificationEmail(subject, fromEmail, toEmail, templateName, parameters));

      userNotificationEmailHistory.setUserNotificationEmailStatus(UserNotificationEmailStatus.SUCCESS);
    } catch (Exception ex) {
      log.error(ex);
      userNotificationEmailHistory.setErrorMessage(ex.getMessage());
      userNotificationEmailHistory.setUserNotificationEmailStatus(UserNotificationEmailStatus.FAIL);
    } finally {
      userNotificationEmailHistory.setId(UUIDs.randomBase64UUID());
      userNotificationEmailHistory.setEventTimestamp(LocalDateTime.now());
      sendDocumentMessage(userNotificationEmailHistory);
    }
  }

  private void addConfirmationLinkParameter(UserEmailNotificationType
      userEmailNotificationType, Map<String, Object> parameters) {

    if (parameters.containsKey(UserEmailNotificationType.getTokenParameterName())) {
      String token = (String) parameters.get(UserEmailNotificationType.getTokenParameterName());

      String userConfirmationUrl = emailConfigParameterRepo.findByParameter(USER_CONFIRMATION_URL).getValue();

      String confirmationUrl = userConfirmationUrl + userEmailNotificationType.getConfirmationLink(token);
      parameters.put("confirmationUrl", confirmationUrl);
    }

  }

}
