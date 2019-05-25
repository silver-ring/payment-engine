package com.optiva.topup.voms.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@EmbeddedKafka
@DirtiesContext
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseKafkaIntegrationTest extends BaseIntegrationTest {

  @Autowired
  private EmbeddedKafkaBroker kafkaEmbedded;

  @Value("${spring.embedded.kafka.brokers}")
  private String brokerAddresses;

  //  @MockBean
  //  private KafkaClient client;
  //
  //  @MockBean
  //  private ConsumerFactory consumerFactory;
  //
  //  @MockBean
  //  private ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory;
}

