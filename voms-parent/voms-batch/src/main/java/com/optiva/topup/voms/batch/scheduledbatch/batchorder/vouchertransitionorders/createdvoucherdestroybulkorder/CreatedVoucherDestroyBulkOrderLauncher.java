package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroybulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_DESTROY_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyBulkOrderLauncher extends
    BulkOrderJobLauncher<CreatedVoucherDestroyBulkOrder> {

  private final CreatedVoucherDestroyBulkOrderJobListener jobListener;
  private final CreatedVoucherDestroyBulkOrderItemWriteListener itemWriteListener;
  private final CreatedVoucherDestroyBulkOrderItemProcessor itemProcessor;
  private final CreatedVoucherDestroyBulkOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherDestroyBulkOrderLauncher(
      CreatedVoucherDestroyBulkOrderJobListener jobListener,
      CreatedVoucherDestroyBulkOrderItemWriteListener itemWriteListener,
      CreatedVoucherDestroyBulkOrderItemProcessor itemProcessor,
      CreatedVoucherDestroyBulkOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.itemWriteListener = itemWriteListener;
    this.itemProcessor = itemProcessor;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_DESTROY_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherDestroyBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherDestroyBulkOrder createdVoucherDestroyBulkOrder) {

    final String stepName = "CreatedVoucherDestroyBulkOrderStep";
    final String readerName = "CreatedVoucherDestroyBulkOrderReader";

    Long startSerialNumber = createdVoucherDestroyBulkOrder.getStartSerialNumber();
    Long endSerialNumber = createdVoucherDestroyBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<CreatedVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, CreatedVoucher.class, startSerialNumber, endSerialNumber);

    JpaItemWriter<DestroyedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter, itemProcessor,
        itemWriteListener);
  }

}
