package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BLOCKED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherModificationBulkOrderLauncher extends
    BulkOrderJobLauncher<BlockedVoucherModificationBulkOrder> {

  private final BlockedVoucherModificationBulkOrderErrorHandler errorHandler;
  private final BlockedVoucherModificationBulkOrderJobListener jobListener;
  private final BlockedVoucherModificationBulkOrderItemProcessor itemProcessor;

  @Autowired
  public BlockedVoucherModificationBulkOrderLauncher(
      BlockedVoucherModificationBulkOrderErrorHandler errorHandler,
      BlockedVoucherModificationBulkOrderJobListener jobListener,
      BlockedVoucherModificationBulkOrderItemProcessor itemProcessor) {
    this.errorHandler = errorHandler;
    this.jobListener = jobListener;
    this.itemProcessor = itemProcessor;
  }

  @KafkaListener(topics = BLOCKED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<BlockedVoucherModificationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(BlockedVoucherModificationBulkOrder bloVouModBulkOrder) {

    final String stepName = "BlockedVoucherModificationBulkOrderStep";
    final String readerName = "BlockedVoucherModificationBulkOrderReader";

    JpaPagingItemReader<BlockedVoucher> itemReader = jobBuilderUtils.readBySerialNumberRange(readerName,
        BlockedVoucher.class,
        bloVouModBulkOrder.getStartSerialNumber(),
        bloVouModBulkOrder.getEndSerialNumber()
    );

    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils
        .buildStep(stepName, itemReader, itemWriter, itemProcessor);
  }

}
