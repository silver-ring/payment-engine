package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.Collections;

public interface WithUserEmailNotification extends WithParametrizedUserEmailNotification {

  default void sendUserEmailNotificationTopic(String email,
      UserEmailNotificationType userEmailNotificationType) {
    sendUserEmailNotificationTopic(email, userEmailNotificationType, Collections.emptyMap());
  }

}
