package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusingbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_USING_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherUsingBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import java.time.LocalDateTime;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherUsingBulkOrderLauncher extends
    BulkOrderJobLauncher<PendingUsageVoucherUsingBulkOrder> {

  private final PendingUsageVoucherUsingBulkOrderItemProcess itemProcess;
  private final PendingUsageVoucherUsingBulkOrderItemWriteListener
      itemWriteListener;
  private final PendingUsageVoucherUsingBulkOrderJobListener jobListener;
  private final PendingUsageVoucherUsingBulkOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherUsingBulkOrderLauncher(
      PendingUsageVoucherUsingBulkOrderItemProcess itemProcess,
      PendingUsageVoucherUsingBulkOrderItemWriteListener itemWriteListener,
      PendingUsageVoucherUsingBulkOrderJobListener jobListener,
      PendingUsageVoucherUsingBulkOrderErrorHandler errorHandler) {
    this.itemProcess = itemProcess;
    this.itemWriteListener =
        itemWriteListener;
    this.jobListener = jobListener;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_USING_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherUsingBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherUsingBulkOrder bulkOrder) {

    final String stepName = "pendingUsageVoucherUsingBulkOrderStep";
    final String readerName = "pendingUsageVoucherUsingBulkOrderReader";
    LocalDateTime pendingRangeStartTime = bulkOrder.getPendingRangeStartTime();
    LocalDateTime pendingRangeEndTime = bulkOrder.getPendingRangeEndTime();
    JpaPagingItemReader<PendingUsageVoucher> itemReader = jobBuilderUtils
        .readByPendingUsageTimeRange(readerName, pendingRangeStartTime, pendingRangeEndTime);
    JpaItemWriter<UsedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);

  }

}
