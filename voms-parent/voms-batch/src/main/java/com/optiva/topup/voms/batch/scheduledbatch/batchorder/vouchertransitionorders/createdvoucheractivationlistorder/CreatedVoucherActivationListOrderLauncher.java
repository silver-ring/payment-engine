package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherActivationListOrderLauncher extends
    ListOrderJobLauncher<CreatedVoucherActivationListOrder> {

  private final CreatedVoucherActivationListOrderJobListener jobListener;
  private final CreatedVoucherActivationListOrderStreamReader streamReader;
  private final CreatedVoucherActivationListOrderItemProcessor itemProcessor;
  private final CreatedVoucherActivationListOrderItemWriteListener
      itemWriteListener;
  private final CreatedVoucherActivationListOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherActivationListOrderLauncher(
      CreatedVoucherActivationListOrderJobListener jobListener,
      CreatedVoucherActivationListOrderStreamReader streamReader,
      CreatedVoucherActivationListOrderItemProcessor itemProcessor,
      CreatedVoucherActivationListOrderItemWriteListener itemWriteListener,
      CreatedVoucherActivationListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener =
        itemWriteListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherActivationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherActivationListOrder batchOrder) {

    final String stepName = "CreatedVoucherActivationListOrderStep";

    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);

  }

}
