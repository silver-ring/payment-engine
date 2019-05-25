package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.VOUCHER_CREATION_BATCH_ORDER_TASK_TOPIC;
import static com.optiva.topup.voms.common.types.BatchOrderConfigParameterType.VOUCHER_ID_LENGTH;

import com.optiva.topup.voms.batch.lanuchers.BatchOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.VoucherCreationBatchOrder;
import com.optiva.topup.voms.common.repositories.VomsGeneratorRepo;
import com.optiva.topup.voms.common.repositories.configparameters.BatchOrderConfigParameterRepo;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreationBatchOrderLauncher extends BatchOrderJobLauncher<VoucherCreationBatchOrder> {

  private final VoucherCreationBatchOrderTasklet tasklet;
  private final VomsGeneratorRepo vomsGeneratorsRepo;
  private final VoucherCreationBatchOrderJobListener jobListener;
  private final BatchOrderConfigParameterRepo configParameterRepo;
  private final VoucherCreationBatchOrderErrorHandler errorHandler;

  @Autowired
  public VoucherCreationBatchOrderLauncher(VoucherCreationBatchOrderTasklet tasklet,
      VomsGeneratorRepo vomsGeneratorsRepo,
      VoucherCreationBatchOrderJobListener jobListener,
      BatchOrderConfigParameterRepo configParameterRepo,
      VoucherCreationBatchOrderErrorHandler errorHandler) {
    this.tasklet = tasklet;
    this.vomsGeneratorsRepo = vomsGeneratorsRepo;
    this.jobListener = jobListener;
    this.configParameterRepo = configParameterRepo;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = VOUCHER_CREATION_BATCH_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected JobParametersBuilder buildJobParameters(VoucherCreationBatchOrder batchOrder) {

    Long startVoucherSerialNumber = vomsGeneratorsRepo.voucherSerialNumber().getValue();
    Long voucherIdLength = Long.parseLong(
        configParameterRepo.findAllAsMap().get(VOUCHER_ID_LENGTH));

    final String startSerialNumberParameterName = "startSerialNumber";
    final String voucherIdLengthParameterName = "voucherIdLength";

    JobParametersBuilder jobParametersBuilder = super.buildJobParameters(batchOrder);
    jobParametersBuilder.addLong(startSerialNumberParameterName, startVoucherSerialNumber);
    jobParametersBuilder.addLong(voucherIdLengthParameterName, voucherIdLength);

    return jobParametersBuilder;
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<VoucherCreationBatchOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(VoucherCreationBatchOrder batchOrder) {
    final String stepName = "VoucherCreationBatchOrderTask";
    return jobBuilderUtils.buildTasklet(stepName, tasklet);
  }

}
