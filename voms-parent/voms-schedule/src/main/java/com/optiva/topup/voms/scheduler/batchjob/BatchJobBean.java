package com.optiva.topup.voms.scheduler.batchjob;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class BatchJobBean extends QuartzJobBean implements WithScheduleBuilder {

  @Override
  protected void executeInternal(JobExecutionContext jobExecutionContext) {
    String topicName =
        (String) jobExecutionContext.getJobDetail().getJobDataMap().get(TOPIC_NAME);
    Integer batchJobId = (Integer) jobExecutionContext.getJobDetail().getJobDataMap()
        .get(BATCH_JOB_ID);
    sendTopic(topicName, batchJobId);
  }

}
