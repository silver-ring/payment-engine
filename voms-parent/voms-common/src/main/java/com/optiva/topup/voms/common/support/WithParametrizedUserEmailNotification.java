package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.messages.UserEmailNotificationMessage;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.Map;

public interface WithParametrizedUserEmailNotification extends WithKafkaSupport {

  String SEND_USER_NOTIFICATION_EMAIL_TOPIC = "SendUserNotificationEmailTopic";

  default void sendUserEmailNotificationTopic(String email,
      UserEmailNotificationType userEmailNotificationType, Map<String, Object> parameters) {
    UserEmailNotificationMessage userEmailNotificationMessage = new UserEmailNotificationMessage();
    userEmailNotificationMessage.setParameters(parameters);
    userEmailNotificationMessage.setEmail(email);
    userEmailNotificationMessage.setUserEmailNotificationType(userEmailNotificationType);
    sendTopic(SEND_USER_NOTIFICATION_EMAIL_TOPIC, userEmailNotificationMessage);
  }

}
