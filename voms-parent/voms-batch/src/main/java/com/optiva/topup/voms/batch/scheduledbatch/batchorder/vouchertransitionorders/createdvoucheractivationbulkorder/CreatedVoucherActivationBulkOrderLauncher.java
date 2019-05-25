package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherActivationBulkOrderLauncher extends
    BulkOrderJobLauncher<CreatedVoucherActivationBulkOrder> {

  private final CreatedVoucherActivationBulkOrderItemProcess itemProcess;
  private final CreatedVoucherActivationBulkOrderItemWriteListener
      itemWriteListener;
  private final CreatedVoucherActivationBulkOrderJobListener jobListener;
  private final CreatedVoucherActivationBulkOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherActivationBulkOrderLauncher(
      CreatedVoucherActivationBulkOrderItemProcess itemProcess,
      CreatedVoucherActivationBulkOrderItemWriteListener itemWriteListener,
      CreatedVoucherActivationBulkOrderJobListener jobListener,
      CreatedVoucherActivationBulkOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.itemProcess = itemProcess;
    this.itemWriteListener =
        itemWriteListener;
    this.jobListener = jobListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherActivationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherActivationBulkOrder createdVouActivationBulkOrder) {

    final String stepName = "CreatedVoucherActivationBulkOrderStep";
    final String readerName = "CreatedVoucherActivationBulkOrderReader";

    Long startSerialNumber = createdVouActivationBulkOrder.getStartSerialNumber();
    Long endSerialNumber = createdVouActivationBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<CreatedVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, CreatedVoucher.class, startSerialNumber, endSerialNumber);
    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);

  }

}
