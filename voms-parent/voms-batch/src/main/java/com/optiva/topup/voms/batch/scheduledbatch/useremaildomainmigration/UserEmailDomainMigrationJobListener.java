package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import static com.optiva.topup.voms.common.types.BatchJobHistoryStatus.DONE;
import static com.optiva.topup.voms.common.types.BatchJobHistoryStatus.STARTED;

import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class UserEmailDomainMigrationJobListener implements JobExecutionListener {

  private final UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo;

  @Autowired
  private UserEmailDomainMigrationHistoryService historyService;

  @Value("#{jobParameters[userEmailDomainMigration]}")
  private UserEmailDomainMigration userEmailDomainMigration;

  @Autowired
  public UserEmailDomainMigrationJobListener(UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo) {
    this.userEmailDomainMigrationRepo = userEmailDomainMigrationRepo;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    userEmailDomainMigrationRepo.delete(userEmailDomainMigration);
    historyService
        .saveProgress(userEmailDomainMigration, STARTED);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    historyService
        .saveProgress(userEmailDomainMigration, DONE);
  }


}
