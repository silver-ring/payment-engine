package com.optiva.topup.voms.scheduler.triggers.useremaildomainmigration;

import static com.optiva.topup.voms.common.support.WithUserEmailDomainMigrationTopicsSupport.USER_EMAIL_DOMAIN_MIGRATION_SCHEDULE_TOPIC;
import static com.optiva.topup.voms.common.support.WithUserEmailDomainMigrationTopicsSupport.USER_EMAIL_DOMAIN_MIGRATION_TOPIC;

import com.optiva.topup.voms.common.messages.UserEmailDomainMigrationMessage;
import com.optiva.topup.voms.common.types.BatchJobScheduleStatus;
import com.optiva.topup.voms.scheduler.batchjob.WithScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEmailDomainMigrationTrigger implements WithScheduleBuilder {

  private final Scheduler scheduler;
  private final UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo;

  @Autowired
  public UserEmailDomainMigrationTrigger(Scheduler scheduler,
      UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo) {
    this.scheduler = scheduler;
    this.userEmailDomainMigrationRepo = userEmailDomainMigrationRepo;
  }

  @KafkaListener(topics = USER_EMAIL_DOMAIN_MIGRATION_SCHEDULE_TOPIC)
  public void buildJobTrigger(UserEmailDomainMigrationMessage message)
      throws SchedulerException {

    JobDetail orderJobDetail =
        buildJobDetail(message.getBatchJobId(), USER_EMAIL_DOMAIN_MIGRATION_TOPIC);

    TriggerKey triggerKey = TriggerKey.triggerKey(message.getBatchJobId().toString(),
        UserEmailDomainMigrationTrigger.class.getName());

    Trigger batchOrderTrigger =
        buildTrigger(triggerKey, orderJobDetail, message.getExecutionTime());

    BatchJobScheduleStatus batchJobScheduleStatus =
        message.getBatchJobScheduleStatus();
    switch (batchJobScheduleStatus) {
      case SCHEDULED:
        if (scheduler.checkExists(batchOrderTrigger.getJobKey())) {
          scheduler.deleteJob(batchOrderTrigger.getJobKey());
        }
        scheduler.scheduleJob(orderJobDetail, batchOrderTrigger);
        break;
      case PAUSED:
        scheduler.pauseTrigger(batchOrderTrigger.getKey());
        break;
      case CANCELED:
        scheduler.deleteJob(batchOrderTrigger.getJobKey());
        userEmailDomainMigrationRepo.deleteById(message.getBatchJobId());
        userEmailDomainMigrationRepo.flush();
        break;
      default:
        // do nothing otherwise
    }

  }

}
