package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BLOCKED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationBulkOrderLauncher extends
    BulkOrderJobLauncher<BlockedVoucherActivationBulkOrder> {

  private final BlockedVoucherActivationBulkOrderItemProcess itemProcess;
  private final BlockedVoucherActivationBulkOrderJobListener jobListener;
  private final BlockedVoucherActivationBulkOrderItemWriteListener
      itemWriteListener;
  private final BlockedVoucherActivationBulkOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public BlockedVoucherActivationBulkOrderLauncher(
      BlockedVoucherActivationBulkOrderItemProcess itemProcess,
      BlockedVoucherActivationBulkOrderJobListener jobListener,
      BlockedVoucherActivationBulkOrderItemWriteListener itemWriteListener,
      BlockedVoucherActivationBulkOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.itemProcess = itemProcess;
    this.jobListener = jobListener;
    this.itemWriteListener =
        itemWriteListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = BLOCKED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<BlockedVoucherActivationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(BlockedVoucherActivationBulkOrder blockedVouActivationBulkOrder) {
    final String stepName = "BlockedVoucherActivationBulkOrderStep";
    final String readerName = "BlockedVoucherActivationBulkOrderReader";

    Long startSerialNumber = blockedVouActivationBulkOrder.getStartSerialNumber();
    Long endSerialNumber = blockedVouActivationBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<BlockedVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, BlockedVoucher.class, startSerialNumber, endSerialNumber);

    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);

  }

}
