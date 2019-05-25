package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherModificationBulkOrderLauncher extends
    BulkOrderJobLauncher<CreatedVoucherModificationBulkOrder> {

  private final CreatedVoucherModificationBulkOrderItemProcessor
      itemProcessor;
  private final CreatedVoucherModificationBulkOrderJobListener jobListener;
  private final CreatedVoucherModificationBulkOrderErrorHandler
      errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherModificationBulkOrderLauncher(
      CreatedVoucherModificationBulkOrderItemProcessor itemProcessor,
      CreatedVoucherModificationBulkOrderJobListener jobListener,
      CreatedVoucherModificationBulkOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.itemProcessor = itemProcessor;
    this.jobListener = jobListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherModificationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherModificationBulkOrder createdVouModBulkOrder) {

    final String stepName = "CreatedVoucherModificationBulkOrderStep";
    final String readerName = "CreatedVoucherModificationBulkOrderReader";

    Long startSerialNumber = createdVouModBulkOrder.getStartSerialNumber();
    Long entSerialNumber = createdVouModBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<CreatedVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, CreatedVoucher.class, startSerialNumber, entSerialNumber);

    JpaItemWriter<CreatedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader,
        itemWriter, itemProcessor);

  }

}
