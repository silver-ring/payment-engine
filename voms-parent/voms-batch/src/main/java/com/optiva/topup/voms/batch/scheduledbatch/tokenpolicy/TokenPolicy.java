package com.optiva.topup.voms.batch.scheduledbatch.tokenpolicy;

import static com.optiva.topup.voms.common.support.WithTokenUserEmailNotification.USER_CONFIRMATION_TOKEN_POLICY_TOPIC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TokenPolicy {

  private final UmUserConfirmationInfoRepo userConfirmationInfoRepo;

  @Autowired
  public TokenPolicy(UmUserConfirmationInfoRepo userConfirmationInfoRepo) {
    this.userConfirmationInfoRepo = userConfirmationInfoRepo;
  }

  @KafkaListener(topics = USER_CONFIRMATION_TOKEN_POLICY_TOPIC)
  public void deleteConfirmationTokenPolicy(Integer id) {
    userConfirmationInfoRepo.deleteById(id);
  }

}
