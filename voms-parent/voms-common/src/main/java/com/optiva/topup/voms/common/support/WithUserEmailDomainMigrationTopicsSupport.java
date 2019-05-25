package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.messages.UserEmailDomainMigrationMessage;

public interface WithUserEmailDomainMigrationTopicsSupport extends WithKafkaSupport {

  String USER_EMAIL_DOMAIN_MIGRATION_TOPIC = "UserEmailDomainMigrationTopic";
  String USER_EMAIL_DOMAIN_MIGRATION_SCHEDULE_TOPIC =
      "UserEmailDomainMigrationScheduleTopic";

  default void sendUserEmailDomainMigrationScheduleTopic(
      UserEmailDomainMigrationMessage message) {
    sendTopic(USER_EMAIL_DOMAIN_MIGRATION_SCHEDULE_TOPIC, message);
  }

}
