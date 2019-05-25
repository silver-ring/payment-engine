package com.optiva.topup.voms.batch.listeners;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public interface JobListener extends JobExecutionListener {

  default void onAfterJob(JobExecution jobExecution) {
    // do nothing
  }

  default void onBeforeJob(JobExecution jobExecution) {
    // do nothing
  }

}
