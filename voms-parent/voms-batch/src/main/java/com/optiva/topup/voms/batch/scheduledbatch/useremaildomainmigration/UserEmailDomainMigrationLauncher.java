package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import static com.optiva.topup.voms.common.support.WithUserEmailDomainMigrationTopicsSupport.USER_EMAIL_DOMAIN_MIGRATION_TOPIC;

import com.optiva.topup.voms.batch.utils.CustomJobParameter;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserEmailDomainMigrationLauncher {

  protected final EntityManagerFactory entityManagerFactory;
  private final UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo;
  private final JobLauncher jobLauncher;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final UserEmailDomainMigrationItemProcessor itemProcessor;
  private final UserEmailDomainMigrationJobListener jobListener;
  private final UserEmailDomainMigrationHistoryService historyService;

  @Autowired
  public UserEmailDomainMigrationLauncher(EntityManagerFactory entityManagerFactory,
      UedmUserEmailDomainMigrationRepo userEmailDomainMigrationRepo, JobLauncher jobLauncher,
      StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory,
      UserEmailDomainMigrationItemProcessor itemProcessor,
      UserEmailDomainMigrationJobListener jobListener,
      UserEmailDomainMigrationHistoryService historyService) {
    this.entityManagerFactory = entityManagerFactory;
    this.userEmailDomainMigrationRepo = userEmailDomainMigrationRepo;
    this.jobLauncher = jobLauncher;
    this.stepBuilderFactory = stepBuilderFactory;
    this.jobBuilderFactory = jobBuilderFactory;
    this.itemProcessor = itemProcessor;
    this.jobListener = jobListener;
    this.historyService = historyService;
  }

  @KafkaListener(topics = USER_EMAIL_DOMAIN_MIGRATION_TOPIC)
  public void onMessage(Integer userEmailDomainMigrationId) {
    UserEmailDomainMigration
        userEmailDomainMigration = userEmailDomainMigrationRepo.findById(userEmailDomainMigrationId).get();
    processJobLaunch(userEmailDomainMigration);
  }

  protected void processJobLaunch(UserEmailDomainMigration userEmailDomainMigration) {
    try {
      JobParametersBuilder jobParameters = buildJobParameters(userEmailDomainMigration);
      jobLauncher.run(job(userEmailDomainMigration), jobParameters.toJobParameters());
    } catch (Exception ex) {
      log.error(ex);
      historyService.saveError(userEmailDomainMigration, ex);
    }
  }

  protected JobParametersBuilder buildJobParameters(UserEmailDomainMigration userEmailDomainMigration) {

    final String jobIdParamName = "id";
    final String batchJobObjParamName = "userEmailDomainMigration";

    CustomJobParameter<UserEmailDomainMigration> batchJobObjParam =
        new CustomJobParameter<>(userEmailDomainMigration);
    JobParametersBuilder jobParameters = new JobParametersBuilder();
    jobParameters.addString(jobIdParamName, UUID.randomUUID().toString());
    jobParameters.addParameter(batchJobObjParamName, batchJobObjParam);
    return jobParameters;
  }

  protected Job job(UserEmailDomainMigration userEmailDomainMigration) {

    return jobBuilderFactory.get("UserEmailDomainMigrationJob")
        .incrementer(new RunIdIncrementer())
        .flow(step(userEmailDomainMigration))
        .end()
        .listener(jobListener)
        .build();
  }

  protected String getJobName() {
    final String jobNamePostfix = "Job";
    final String extracted = "Launcher";
    return this.getClass().getSimpleName().replace(extracted, "") + jobNamePostfix;
  }

  protected Step step(UserEmailDomainMigration userEmailDomainMigration) {
    final String stepName = "UserEmailDomainMigrationStep";
    final String readerName = "UserEmailDomainMigrationReader";

    String selectAllUserAccounts = "select userAccount from UserAccount userAccount";

    JpaPagingItemReader<UserAccount> itemReader = new JpaPagingItemReaderBuilder<UserAccount>()
        .name(readerName).queryString(selectAllUserAccounts)
        .pageSize(100)
        .entityManagerFactory(entityManagerFactory)
        .build();

    JpaItemWriter<UserAccount> itemWriter = new JpaItemWriterBuilder<UserAccount>()
        .entityManagerFactory(entityManagerFactory)
        .build();

    return stepBuilderFactory.get(stepName).<UserAccount, UserAccount>chunk(100)
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .build();

  }

}
