package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import org.springframework.kafka.core.KafkaTemplate;

public interface WithKafkaSupport {

  default void sendTopic(String topicName, Object data) {
    KafkaTemplate kafkaTemplate = ApplicationContextUtil.getApplicationContext().getBean(KafkaTemplate.class);
    kafkaTemplate.send(topicName, data);
  }

}
