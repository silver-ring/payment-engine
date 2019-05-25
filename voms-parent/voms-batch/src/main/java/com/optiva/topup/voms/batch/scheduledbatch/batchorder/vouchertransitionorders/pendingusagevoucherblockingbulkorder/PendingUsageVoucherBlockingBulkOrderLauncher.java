package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockingbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.time.LocalDateTime;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingBulkOrderLauncher extends
    BulkOrderJobLauncher<PendingUsageVoucherBlockingBulkOrder> {

  private final PendingUsageVoucherBlockingBulkOrderItemProcess itemProcess;
  private final PendingUsageVoucherBlockingBulkOrderItemWriteListener writeListener;
  private final PendingUsageVoucherBlockingBulkOrderJobListener jobListener;
  private final PendingUsageVoucherBlockingBulkOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherBlockingBulkOrderLauncher(
      PendingUsageVoucherBlockingBulkOrderItemProcess itemProcess,
      PendingUsageVoucherBlockingBulkOrderItemWriteListener writeListener,
      PendingUsageVoucherBlockingBulkOrderJobListener jobListener,
      PendingUsageVoucherBlockingBulkOrderErrorHandler errorHandler) {
    this.itemProcess = itemProcess;
    this.writeListener = writeListener;
    this.jobListener = jobListener;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherBlockingBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherBlockingBulkOrder bulkOrder) {

    final String stepName = "PendingUsageVoucherBlockingBulkOrderStep";
    final String readerName = "pendingUsageVoucherBlockingBulkOrderReader";
    LocalDateTime pendingRangeStartTime = bulkOrder.getPendingRangeStartTime();
    LocalDateTime pendingRangeEndTime = bulkOrder.getPendingRangeEndTime();
    JpaPagingItemReader<PendingUsageVoucher> itemReader =
        jobBuilderUtils.readByPendingUsageTimeRange(readerName, pendingRangeStartTime, pendingRangeEndTime);
    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, writeListener);
  }

}
