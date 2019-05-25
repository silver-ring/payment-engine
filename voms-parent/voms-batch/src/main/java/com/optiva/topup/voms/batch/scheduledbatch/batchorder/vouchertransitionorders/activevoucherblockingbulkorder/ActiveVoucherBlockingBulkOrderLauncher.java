package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockingbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.ACTIVE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.ActiveVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingBulkOrderLauncher extends
    BulkOrderJobLauncher<ActiveVoucherBlockingBulkOrder> {

  private final ActiveVoucherBlockingBulkOrderJobListener jobListener;
  private final ActiveVoucherBlockingBulkOrderItemProcess itemProcess;
  private final ActiveVoucherBlockingBulkOrderItemWriteListener writeListener;
  private final ActiveVoucherBlockingBulkOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public ActiveVoucherBlockingBulkOrderLauncher(
      ActiveVoucherBlockingBulkOrderJobListener jobListener,
      ActiveVoucherBlockingBulkOrderItemProcess itemProcess,
      ActiveVoucherBlockingBulkOrderItemWriteListener writeListener,
      ActiveVoucherBlockingBulkOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.itemProcess = itemProcess;
    this.writeListener = writeListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = ACTIVE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<ActiveVoucherBlockingBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(ActiveVoucherBlockingBulkOrder activeVoucherBlockingBulkOrder) {

    final String stepName = "voucherBlockingOrderStep";
    final String readerName = "ActiveVoucherBlockingBulkOrderReader";

    Long startSerialNumber = activeVoucherBlockingBulkOrder.getStartSerialNumber();
    Long endSerialNumber = activeVoucherBlockingBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<ActiveVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, ActiveVoucher.class, startSerialNumber, endSerialNumber);

    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, writeListener);
  }

}
