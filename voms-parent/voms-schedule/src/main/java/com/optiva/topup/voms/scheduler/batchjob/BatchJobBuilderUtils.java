package com.optiva.topup.voms.scheduler.batchjob;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchJobBuilderUtils {

  public static JobDetail build(Integer id, String topicName) {
    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put(BatchJobParametersHelper.BATCH_JOB_ID, id);
    jobDataMap.put(BatchJobParametersHelper.TOPIC_NAME, topicName);

    return JobBuilder.newJob(BatchJobBean.class)
        .withIdentity(id.toString(), topicName)
        .usingJobData(jobDataMap)
        .build();
  }

}
