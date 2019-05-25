package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.batch.listeners.VoucherPolicyJobListener;
import com.optiva.topup.voms.batch.utils.CustomJobParameter;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VoucherPolicyJobLauncher {

  @Autowired
  protected JobLauncher jobLauncher;
  @Autowired
  protected JobBuilderFactory jobBuilderFactory;
  @Autowired
  protected StepBuilderFactory stepBuilderFactory;
  @Autowired
  protected VoucherPolicyScheduleRepo voucherPolicyScheduleRepo;
  @Autowired
  protected DefaultVoucherPolicyErrorHandler errorHandler;
  @Autowired
  protected DefaultVoucherPolicyJobListener jobListener;
  @Autowired
  protected EntityManagerFactory entityManagerFactory;
  @Autowired
  protected JobBuilderUtils jobBuilderUtils;

  public abstract void onMessage(Integer policyId);

  protected void launch(Integer policyId) {
    VoucherPolicySchedule voucherPolicySchedule = voucherPolicyScheduleRepo
        .findById(policyId).get();
    try {
      JobParametersBuilder jobParameters = buildJobParameters(voucherPolicySchedule);
      jobLauncher.run(job(voucherPolicySchedule), jobParameters.toJobParameters());
    } catch (Exception ex) {
      onLaunchError(voucherPolicySchedule, new VoucherPolicyLauncherException(ex));
    }
  }

  protected JobParametersBuilder buildJobParameters(VoucherPolicySchedule voucherPolicySchedule) {
    String jobIdParameterName = "id";
    String voucherPolicyParamName = "voucherPolicySchedule";

    JobParametersBuilder jobParameters = new JobParametersBuilder();
    CustomJobParameter<VoucherPolicySchedule> voucherPolicyScheduleParameter = new CustomJobParameter<>(
        voucherPolicySchedule);
    jobParameters.addString(jobIdParameterName, UUID.randomUUID().toString());
    jobParameters.addParameter(voucherPolicyParamName, voucherPolicyScheduleParameter);
    return jobParameters;
  }

  protected void onLaunchError(VoucherPolicySchedule voucherPolicySchedule, Exception ex) {
    errorHandler.saveErrorVoucherPolicyHistory(voucherPolicySchedule, ex);
  }

  protected Job job(VoucherPolicySchedule voucherPolicySchedule) {
    return jobBuilderUtils.buildJob(getJobName(), step(voucherPolicySchedule), getJobListener());
  }

  protected VoucherPolicyJobListener getJobListener() {
    return jobListener;
  }

  protected String getJobName() {
    final String jobNamePostfix = "Job";
    final String extracted = "Launcher";
    return this.getClass().getSimpleName().replace(extracted, "") + jobNamePostfix;
  }

  protected abstract Step step(VoucherPolicySchedule voucherPolicySchedule);

}
