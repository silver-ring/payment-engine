package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationListOrderLauncher extends
    ListOrderJobLauncher<BlockedVoucherActivationListOrder> {

  private final BlockedVoucherActivationListOrderJobListener jobListener;
  private final BlockedVoucherActivationListOrderStreamReader streamReader;
  private final BlockedVoucherActivationListOrderItemProcessor itemProcessor;
  private final BlockedVoucherActivationListOrderItemWriteListener
      itemWriteListener;
  private final BlockedVoucherActivationListOrderErrorHandler errorHandler;

  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public BlockedVoucherActivationListOrderLauncher(
      BlockedVoucherActivationListOrderJobListener jobListener,
      BlockedVoucherActivationListOrderStreamReader streamReader,
      BlockedVoucherActivationListOrderItemProcessor itemProcessor,
      BlockedVoucherActivationListOrderItemWriteListener itemWriteListener,
      BlockedVoucherActivationListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener =
        itemWriteListener;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<BlockedVoucherActivationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(BlockedVoucherActivationListOrder batchOrder) {

    final String stepName = "BlockedVoucherActivationListOrderStep";

    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor, itemWriteListener);
  }

}
