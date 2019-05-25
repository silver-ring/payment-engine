package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherModificationListOrderLauncher extends
    ListOrderJobLauncher<CreatedVoucherModificationListOrder> {

  private final CreatedVoucherModificationListOrderItemProcessor itemProcessor;
  private final CreatedVoucherModificationListOrderJobListener jobListener;
  private final CreatedVoucherModificationListOrderStreamReader streamReader;
  private final CreatedVoucherModificationListOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public CreatedVoucherModificationListOrderLauncher(
      CreatedVoucherModificationListOrderItemProcessor itemProcessor,
      CreatedVoucherModificationListOrderJobListener jobListener,
      CreatedVoucherModificationListOrderStreamReader streamReader,
      CreatedVoucherModificationListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.itemProcessor = itemProcessor;
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = CREATED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<CreatedVoucherModificationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(CreatedVoucherModificationListOrder batchOrder) {
    final String stepName = "CreatedVoucherModificationListOrderStep";
    JpaItemWriter<CreatedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();
    return jobBuilderUtils.buildStep(stepName, streamReader,
        itemWriter, itemProcessor);
  }

}
