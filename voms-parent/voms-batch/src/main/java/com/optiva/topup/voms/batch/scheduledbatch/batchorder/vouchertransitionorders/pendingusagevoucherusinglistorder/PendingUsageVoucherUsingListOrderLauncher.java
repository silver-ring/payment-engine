package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusinglistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_USING_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherUsingListOrder;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherUsingListOrderLauncher extends
    ListOrderJobLauncher<PendingUsageVoucherUsingListOrder> {

  private final PendingUsageVoucherUsingListOrderJobListener jobListener;
  private final PendingUsageVoucherUsingListOrderStreamReader streamReader;
  private final PendingUsageVoucherUsingListOrderItemProcessor itemProcessor;
  private final PendingUsageVoucherUsingListOrderItemWriteListener
      itemWriteListener;
  private final PendingUsageVoucherUsingListOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherUsingListOrderLauncher(
      PendingUsageVoucherUsingListOrderJobListener jobListener,
      PendingUsageVoucherUsingListOrderStreamReader streamReader,
      PendingUsageVoucherUsingListOrderItemProcessor itemProcessor,
      PendingUsageVoucherUsingListOrderItemWriteListener itemWriteListener,
      PendingUsageVoucherUsingListOrderErrorHandler errorHandler) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener =
        itemWriteListener;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_USING_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherUsingListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherUsingListOrder batchOrder) {

    final String stepName = "PendingUsageVoucherUsingListOrderStep";
    JpaItemWriter<UsedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);
  }
}
