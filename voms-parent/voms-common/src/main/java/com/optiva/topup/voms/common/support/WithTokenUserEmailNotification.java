package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.HashMap;
import java.util.Map;

public interface WithTokenUserEmailNotification extends WithParametrizedUserEmailNotification {

  String USER_CONFIRMATION_TOKEN_POLICY_TOPIC = "UserConfirmationTokenPolicyTopic";
  String USER_CONFIRMATION_TOKEN_POLICY_SCHEDULE_TOPIC =
      "UserConfirmationTokenPolicyScheduleTopic";

  default void sendTokenUserEmailNotificationTopic(UserConfirmationToken userConfirmationToken,
      UserEmailNotificationType userEmailNotificationType) {
    Map<String, Object> parameters = new HashMap<>();
    parameters.put(UserEmailNotificationType.getTokenParameterName(), userConfirmationToken.getToken());
    sendUserEmailNotificationTopic(userConfirmationToken.getEmail(), userEmailNotificationType, parameters);
    sendTopic(USER_CONFIRMATION_TOKEN_POLICY_SCHEDULE_TOPIC, userConfirmationToken.getId());
  }

}
