package com.optiva.topup.voms.scheduler.batchjob;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.optiva.topup.voms.common.support.WithKafkaSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

public interface WithScheduleBuilder extends WithKafkaSupport {

  String TOPIC_NAME = "topicName";
  String BATCH_JOB_ID = "batchJobId";

  default JobDetail buildJobDetail(Integer id, String topicName) {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(BATCH_JOB_ID, id);
    jobDataMap.put(TOPIC_NAME, topicName);

    return JobBuilder.newJob(BatchJobBean.class)
        .withIdentity(id.toString(), topicName)
        .usingJobData(jobDataMap)
        .build();
  }

  default Trigger buildTrigger(TriggerKey triggerKey, JobDetail jobDetail, LocalDateTime executionTime) {
    Date executionDate = Date.from(executionTime.atZone(ZoneId.systemDefault()).toInstant());
    return TriggerBuilder
        .newTrigger()
        .forJob(jobDetail)
        .withIdentity(triggerKey)
        .startAt(executionDate)
        .build();
  }

  default Trigger buildTrigger(TriggerKey triggerKey, JobDetail jobDetail, String cronExpression) {

    CronScheduleBuilder cronScheduleBuilder = cronSchedule(cronExpression)
        .withMisfireHandlingInstructionDoNothing();

    return TriggerBuilder.newTrigger()
        .withIdentity(triggerKey)
        .withSchedule(cronScheduleBuilder)
        .forJob(jobDetail)
        .build();
  }

}
