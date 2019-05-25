package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroylistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_DESTROY_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyListOrder;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyListOrderLauncher extends
    ListOrderJobLauncher<CreatedVoucherDestroyListOrder> {

  private final CreatedVoucherDestroyListOrderJobListener jobListener;
  private final CreatedVoucherDestroyListOrderStreamReader streamReader;
  private final CreatedVoucherDestroyListOrderItemProcessor itemProcessor;
  private final CreatedVoucherDestroyListOrderItemWriteListener itemWriteListener;
  private final CreatedVoucherDestroyListOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherDestroyListOrderLauncher(
      CreatedVoucherDestroyListOrderJobListener jobListener,
      CreatedVoucherDestroyListOrderStreamReader streamReader,
      CreatedVoucherDestroyListOrderItemProcessor itemProcessor,
      CreatedVoucherDestroyListOrderItemWriteListener itemWriteListener,
      CreatedVoucherDestroyListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener = itemWriteListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_DESTROY_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherDestroyListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherDestroyListOrder batchOrder) {
    final String stepName = "CreatedVoucherActivationListOrderStep";

    JpaItemWriter<DestroyedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);
  }

}
