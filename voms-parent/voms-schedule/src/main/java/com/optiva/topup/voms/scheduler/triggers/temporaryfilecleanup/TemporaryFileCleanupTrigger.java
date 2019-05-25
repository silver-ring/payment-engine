package com.optiva.topup.voms.scheduler.triggers.temporaryfilecleanup;

import static com.optiva.topup.voms.common.support.WithFileTopicsSupport.TEMPORARY_FILE_CLEANUP_SCHEDULE_TOPIC;
import static com.optiva.topup.voms.common.support.WithFileTopicsSupport.TEMPORARY_FILE_CLEANUP_TOPIC;

import com.optiva.topup.voms.common.entities.configparameters.FileManagerConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.common.types.FileManagerConfigParameterType;
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
public class TemporaryFileCleanupTrigger implements WithScheduleBuilder {

  private final Scheduler scheduler;
  private final FileManagerConfigParameterRepo fileManagerConfigParameterRepo;

  @Autowired
  public TemporaryFileCleanupTrigger(Scheduler scheduler,
      FileManagerConfigParameterRepo fileManagerConfigParameterRepo) {
    this.scheduler = scheduler;
    this.fileManagerConfigParameterRepo = fileManagerConfigParameterRepo;
  }

  @KafkaListener(topics = TEMPORARY_FILE_CLEANUP_SCHEDULE_TOPIC)
  public void buildJobTrigger(Integer tempFileId) throws SchedulerException {

    FileManagerConfigParameter fileManagerConfigParameter = fileManagerConfigParameterRepo
        .findByParameter(FileManagerConfigParameterType.TEMPORARY_FILE_KEEP_DURATION);

    Integer cleanupDuration = Integer.parseInt(fileManagerConfigParameter.getValue());

    LocalDateTime executionLocalDateTime = LocalDateTime.now().plusMinutes(cleanupDuration);

    JobDetail tempFileCleanupJobDetails = buildJobDetail(tempFileId, TEMPORARY_FILE_CLEANUP_TOPIC);
    TriggerKey triggerKey = TriggerKey.triggerKey(tempFileId.toString(), this.getClass().getName());
    Trigger tempFileCleanupTrigger =
        buildTrigger(triggerKey, tempFileCleanupJobDetails, executionLocalDateTime);

    if (scheduler.checkExists(tempFileCleanupTrigger.getJobKey())) {
      scheduler.deleteJob(tempFileCleanupTrigger.getJobKey());
    }

    scheduler.scheduleJob(tempFileCleanupJobDetails, tempFileCleanupTrigger);
  }

}
