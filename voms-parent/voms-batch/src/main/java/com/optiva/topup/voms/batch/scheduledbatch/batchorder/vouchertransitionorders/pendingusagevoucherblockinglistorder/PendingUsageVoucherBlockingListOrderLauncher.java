package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockinglistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingListOrder;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingListOrderLauncher extends
    ListOrderJobLauncher<PendingUsageVoucherBlockingListOrder> {

  private final PendingUsageVoucherBlockingListOrderJobListener jobListener;
  private final PendingUsageVoucherBlockingListOrderStreamReader streamReader;
  private final PendingUsageVoucherBlockingListOrderItemProcessor itemProcessor;
  private final PendingUsageVoucherBlockingListOrderItemWriteListener itemWriteListener;
  private final PendingUsageVoucherBlockingListOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherBlockingListOrderLauncher(
      PendingUsageVoucherBlockingListOrderJobListener jobListener,
      PendingUsageVoucherBlockingListOrderStreamReader streamReader,
      PendingUsageVoucherBlockingListOrderItemProcessor itemProcessor,
      PendingUsageVoucherBlockingListOrderItemWriteListener itemWriteListener,
      PendingUsageVoucherBlockingListOrderErrorHandler errorHandler) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener = itemWriteListener;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherBlockingListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherBlockingListOrder batchOrder) {

    final String stepName = "PendingUsageVoucherBlockingListOrderStep";
    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);
  }

}
