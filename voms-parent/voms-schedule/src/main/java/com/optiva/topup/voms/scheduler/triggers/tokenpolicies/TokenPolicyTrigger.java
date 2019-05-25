package com.optiva.topup.voms.scheduler.triggers.tokenpolicies;

import static com.optiva.topup.voms.common.support.WithTokenUserEmailNotification.USER_CONFIRMATION_TOKEN_POLICY_SCHEDULE_TOPIC;
import static com.optiva.topup.voms.common.support.WithTokenUserEmailNotification.USER_CONFIRMATION_TOKEN_POLICY_TOPIC;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.USER_CONFIRMATION_TOKEN_EXPIRATION_DURATION;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.scheduler.batchjob.WithScheduleBuilder;
import java.time.LocalDateTime;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TokenPolicyTrigger implements WithScheduleBuilder {

  private final Scheduler scheduler;
  private final UserManagementConfigParameterRepo configParameterRepo;

  @Autowired
  public TokenPolicyTrigger(Scheduler scheduler,
      UserManagementConfigParameterRepo configParameterRepo) {
    this.scheduler = scheduler;
    this.configParameterRepo = configParameterRepo;
  }

  @KafkaListener(topics = USER_CONFIRMATION_TOKEN_POLICY_SCHEDULE_TOPIC)
  public void buildJobTrigger(Integer tokenPolicyId) throws SchedulerException {

    UserManagementConfigParameter configParameter =
        configParameterRepo.findByParameter(USER_CONFIRMATION_TOKEN_EXPIRATION_DURATION);

    LocalDateTime executionLocalDateTime =
        LocalDateTime.now().plusMinutes(Long.parseLong(configParameter.getValue()));

    JobDetail tokenPolicyJobDetails = buildJobDetail(tokenPolicyId, USER_CONFIRMATION_TOKEN_POLICY_TOPIC);
    TriggerKey triggerKey = TriggerKey.triggerKey(tokenPolicyId.toString(), this.getClass().getName());
    Trigger tokenPolicyTrigger = buildTrigger(triggerKey, tokenPolicyJobDetails, executionLocalDateTime);

    if (scheduler.checkExists(tokenPolicyTrigger.getJobKey())) {
      scheduler.deleteJob(tokenPolicyTrigger.getJobKey());
    }

    scheduler.scheduleJob(tokenPolicyJobDetails, tokenPolicyTrigger);
  }

}
