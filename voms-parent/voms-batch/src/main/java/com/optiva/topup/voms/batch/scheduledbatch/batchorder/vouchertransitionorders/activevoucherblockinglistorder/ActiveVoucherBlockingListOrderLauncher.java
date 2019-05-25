package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockinglistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.ACTIVE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.ActiveVoucherBlockingListOrder;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingListOrderLauncher extends
    ListOrderJobLauncher<ActiveVoucherBlockingListOrder> {

  private final ActiveVoucherBlockingListOrderJobListener jobListener;
  private final ActiveVoucherBlockingListOrderStreamReader streamReader;
  private final ActiveVoucherBlockingListOrderItemProcessor itemProcessor;
  private final ActiveVoucherBlockingListOrderItemWriteListener itemWriteListener;
  private final ActiveVoucherBlockingListOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public ActiveVoucherBlockingListOrderLauncher(
      ActiveVoucherBlockingListOrderJobListener jobListener,
      ActiveVoucherBlockingListOrderStreamReader streamReader,
      ActiveVoucherBlockingListOrderItemProcessor itemProcessor,
      ActiveVoucherBlockingListOrderItemWriteListener itemWriteListener,
      ActiveVoucherBlockingListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener = itemWriteListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = ACTIVE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<ActiveVoucherBlockingListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(ActiveVoucherBlockingListOrder batchOrder) {
    final String stepName = "ActiveVoucherBlockingListOrderStep";

    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);

  }

}
