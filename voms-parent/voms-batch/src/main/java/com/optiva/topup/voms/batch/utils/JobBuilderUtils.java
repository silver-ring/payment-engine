package com.optiva.topup.voms.batch.utils;

import com.optiva.topup.voms.batch.listeners.JobListener;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobBuilderUtils {

  private final EntityManagerFactory entityManagerFactory;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Autowired
  public JobBuilderUtils(EntityManagerFactory entityManagerFactory, JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory) {
    this.entityManagerFactory = entityManagerFactory;
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  public <T extends Voucher> JpaPagingItemReader<T> buildJpaItemReader(String readerName, String sql,
      Map<String, Object> params) {
    return new JpaPagingItemReaderBuilder<T>()
        .name(readerName).queryString(sql)
        .pageSize(getPageSize()).parameterValues(params)
        .entityManagerFactory(entityManagerFactory).build();
  }

  public <T extends Voucher> JpaPagingItemReader<T> buildJpaItemReader(String readerName, String sql) {
    return new JpaPagingItemReaderBuilder<T>()
        .name(readerName).queryString(sql)
        .pageSize(getPageSize())
        .entityManagerFactory(entityManagerFactory).build();
  }

  public <T extends Voucher> JpaItemWriter<T> buildJpaItemWriter() {
    return new JpaItemWriterBuilder<T>().entityManagerFactory(entityManagerFactory).build();
  }

  public <T extends Voucher> JpaPagingItemReader<T> readBySerialNumberRange(String readerName,
      Class<T> entityType, Long startSerialNumber, Long endSerialNumber) {

    final String entityName = entityType.getSimpleName();

    final String startSerialNumberParameterName = "startSerialNumber";
    final String endSerialNumberParameterName = "endSerialNumber";
    final String readerFormattedSql =
        "select v from %1$s v Where v.serialNumber Between :%2$s and :%3$s ";

    final String readerSql =
        String.format(readerFormattedSql,
            entityName, startSerialNumberParameterName, endSerialNumberParameterName);

    Map<String, Object> params = new HashMap<>();
    params.put(startSerialNumberParameterName, startSerialNumber);
    params.put(endSerialNumberParameterName, endSerialNumber);
    return this.buildJpaItemReader(readerName, readerSql, params);
  }

  public JpaPagingItemReader<PendingUsageVoucher> readByPendingUsageTimeRange(String readerName,
      LocalDateTime pendingRangeStartTime, LocalDateTime pendingRangeEndTime) {

    final String startRangeParamName = "pendingRangeStartTime";
    final String endRangeParamName = "pendingRangeEndTime";

    String readerFormattedSql =
        "SELECT v FROM PendingUsageVoucher v WHERE v.usageRequestTime BETWEEN :%1$s AND :%2$s";

    final String readerSql = String.format(
        readerFormattedSql,
        startRangeParamName, endRangeParamName);

    Map<String, Object> params = new HashMap<>();
    params.put(startRangeParamName, pendingRangeStartTime);
    params.put(endRangeParamName, pendingRangeEndTime);
    return this
        .buildJpaItemReader(readerName, readerSql,
            params);
  }

  public <T extends Voucher> JpaPagingItemReader<T> readExpiredVouchers(String readerName,
      Class<T> entityType) {
    LocalDate keepDate = LocalDate.now();
    return readExpiredVouchers(readerName, entityType, keepDate);
  }

  public <T extends Voucher> JpaPagingItemReader<T> readExpiredVouchers(String readerName,
      Class<T> entityType, LocalDate keepDate) {

    String keepDateParameterName = "keepDate";
    final String entityName = entityType.getSimpleName();
    String formattedSql = "select v from %1$s v Where v.expirationDate < :%2$s";

    final String sql = String.format(
        formattedSql,
        entityName, keepDateParameterName);

    Map<String, Object> params = new HashMap<>();
    params.put(keepDateParameterName, keepDate);

    return new JpaPagingItemReaderBuilder<T>()
        .name(readerName)
        .queryString(sql)
        .pageSize(getPageSize())
        .parameterValues(params)
        .entityManagerFactory(entityManagerFactory)
        .build();
  }

  public <T extends Voucher> JpaPagingItemReader<T> readAllVouchers(String readerName, Class<T> entityType) {

    String formattedSql = "select v from %1$s v";
    final String entityName = entityType.getSimpleName();

    final String sql = String.format(
        formattedSql, entityName);

    return new JpaPagingItemReaderBuilder<T>()
        .name(readerName)
        .queryString(sql)
        .pageSize(getPageSize())
        .entityManagerFactory(entityManagerFactory)
        .build();
  }

  public <I, O> Step buildStep(String stepName, ItemReader<I> itemReader,
      ItemWriter<O> itemWriter, ItemProcessor<I, O> itemProcessor, Object stepListener) {
    return stepBuilderFactory.get(stepName).<I, O>chunk(getChunkSize())
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .listener(stepListener)
        .build();
  }

  public <I, O> Step buildStep(String stepName, ItemReader<I> itemReader,
      ItemWriter<O> itemWriter, ItemProcessor<I, O> itemProcessor) {
    return stepBuilderFactory.get(stepName).<I, O>chunk(getChunkSize())
        .reader(itemReader)
        .processor(itemProcessor)
        .writer(itemWriter)
        .build();
  }

  public <I, O> Step buildStep(String stepName, ItemReader<I> itemReader,
      ItemWriter<O> itemWriter) {
    return stepBuilderFactory.get(stepName).<I, O>chunk(getChunkSize())
        .reader(itemReader)
        .writer(itemWriter)
        .build();
  }

  public Step buildTasklet(String stepName, Tasklet tasklet) {
    return stepBuilderFactory.get(stepName).tasklet(tasklet).build();
  }

  public Job buildJob(String jobName, Step step, JobListener jobListener) {
    return jobBuilderFactory.get(jobName)
        .incrementer(new RunIdIncrementer())
        .flow(step).end()
        .listener(jobListener)
        .build();
  }

  public int getPageSize() {
    return 1000;
  }

  public int getChunkSize() {
    return 100;
  }

}
