package com.optiva.topup.voms.common.support;

public interface WithFileTopicsSupport extends WithKafkaSupport {

  String TEMPORARY_FILE_CLEANUP_SCHEDULE_TOPIC = "TemporaryFileCleanupScheduleTopic";
  String TEMPORARY_FILE_CLEANUP_TOPIC = "TemporaryFileCleanupTopic";

  default void sendTemporaryFileCleanupScheduleTopic(Integer id) {
    sendTopic(TEMPORARY_FILE_CLEANUP_SCHEDULE_TOPIC, id);
  }

  default void sendTemporaryFileCleanupTopic(Integer id) {
    sendTopic(TEMPORARY_FILE_CLEANUP_TOPIC, id);
  }

}
