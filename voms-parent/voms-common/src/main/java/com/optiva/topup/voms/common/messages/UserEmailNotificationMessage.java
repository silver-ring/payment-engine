package com.optiva.topup.voms.common.messages;

import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailNotificationMessage {

  private String email;
  private UserEmailNotificationType userEmailNotificationType;
  private Map<String, Object> parameters;

}
