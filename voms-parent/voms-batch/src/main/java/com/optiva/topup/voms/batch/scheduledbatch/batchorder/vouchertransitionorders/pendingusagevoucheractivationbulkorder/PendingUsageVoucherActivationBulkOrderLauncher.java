package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.time.LocalDateTime;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationBulkOrderLauncher extends
    BulkOrderJobLauncher<PendingUsageVoucherActivationBulkOrder> {

  private final PendingUsageVoucherActivationBulkOrderItemProcess itemProcess;
  private final PendingUsageVoucherActivationBulkOrderItemWriteListener itemWriteListener;
  private final PendingUsageVoucherActivationBulkOrderJobListener jobListener;
  private final PendingUsageVoucherActivationBulkOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherActivationBulkOrderLauncher(
      PendingUsageVoucherActivationBulkOrderItemProcess itemProcess,
      PendingUsageVoucherActivationBulkOrderItemWriteListener itemWriteListener,
      PendingUsageVoucherActivationBulkOrderJobListener pendUsageVouActBlkOrdJobList,
      PendingUsageVoucherActivationBulkOrderErrorHandler errorHandler) {
    this.itemProcess = itemProcess;
    this.itemWriteListener = itemWriteListener;
    this.jobListener = pendUsageVouActBlkOrdJobList;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherActivationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherActivationBulkOrder bulkOrder) {

    final String stepName = "PendingUsageVoucherActivationBulkOrderStep";
    final String readerName = "PendingUsageVoucherActivationBulkOrderStep";
    LocalDateTime pendingRangeStartTime = bulkOrder.getPendingRangeStartTime();
    LocalDateTime pendingRangeEndTime = bulkOrder.getPendingRangeEndTime();
    JpaPagingItemReader<PendingUsageVoucher> itemReader =
        jobBuilderUtils.readByPendingUsageTimeRange(readerName, pendingRangeStartTime, pendingRangeEndTime);
    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess,
        itemWriteListener);

  }

}
