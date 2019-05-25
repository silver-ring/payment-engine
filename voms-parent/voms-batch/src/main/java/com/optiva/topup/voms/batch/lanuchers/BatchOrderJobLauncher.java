package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.CustomJobParameter;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import java.lang.reflect.ParameterizedType;
import java.util.UUID;
import javax.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public abstract class BatchOrderJobLauncher<T extends BatchOrder> {

  @Autowired
  protected EntityManager entityManager;
  @Autowired
  protected JobBuilderUtils jobBuilderUtils;
  @Autowired
  private JobLauncher jobLauncher;

  public abstract void onMessage(Integer orderId);

  protected void launch(Integer orderId) {
    T batchOrder = findBatchOrder(orderId);
    processJobLaunch(batchOrder);
  }

  protected T findBatchOrder(Integer orderId) {
    return entityManager.find(getBatchOrderType(), orderId);
  }

  protected void processJobLaunch(T batchOrder) {
    try {
      JobParametersBuilder jobParameters = buildJobParameters(batchOrder);
      jobLauncher.run(job(batchOrder), jobParameters.toJobParameters());
    } catch (Exception ex) {
      onLaunchError(batchOrder, new BatchOrderLauncherException(ex));
    }
  }

  protected JobParametersBuilder buildJobParameters(T batchOrder) {

    final String jobIdParameterName = "id";
    final String batchOrderParameterName = "batchOrder";

    CustomJobParameter<BatchOrder> batchOrderParameter = new CustomJobParameter<>(batchOrder);
    JobParametersBuilder jobParameters = new JobParametersBuilder();
    jobParameters.addString(jobIdParameterName, UUID.randomUUID().toString());
    jobParameters.addParameter(batchOrderParameterName, batchOrderParameter);
    return jobParameters;
  }

  protected void onLaunchError(T batchOrder, Exception ex) {
    getErrorHandler().saveErrorBatchOrderHistory(batchOrder, ex);
  }

  protected Job job(T batchOrder) {
    return jobBuilderUtils.buildJob(getJobName(), step(batchOrder), getJobListener());
  }

  protected String getJobName() {
    final String jobNamePostfix = "Job";
    final String extracted = "Launcher";
    return this.getClass().getSimpleName().replace(extracted, "") + jobNamePostfix;
  }

  protected abstract BatchOrderJobListener getJobListener();

  protected abstract BatchOrderErrorHandler<T> getErrorHandler();

  protected abstract Step step(T batchOrder);

  protected Class<T> getBatchOrderType() {
    return (Class<T>) (((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0]);
  }

}
