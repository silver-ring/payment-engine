package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherActivationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationListOrderLauncher extends
    ListOrderJobLauncher<PendingUsageVoucherActivationListOrder> {

  private final PendingUsageVoucherActivationListOrderJobListener jobListener;
  private final PendingUsageVoucherActivationListOrderStreamReader streamReader;
  private final PendingUsageVoucherActivationListOrderItemProcessor itemProcessor;
  private final PendingUsageVoucherActivationListOrderItemWriteListener itemWriteListener;
  private final PendingUsageVoucherActivationListOrderErrorHandler errorHandler;

  @Autowired
  public PendingUsageVoucherActivationListOrderLauncher(
      PendingUsageVoucherActivationListOrderJobListener jobListener,
      PendingUsageVoucherActivationListOrderStreamReader streamReader,
      PendingUsageVoucherActivationListOrderItemProcessor itemProcessor,
      PendingUsageVoucherActivationListOrderItemWriteListener itemWriteListener,
      PendingUsageVoucherActivationListOrderErrorHandler errorHandler) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.itemProcessor = itemProcessor;
    this.itemWriteListener = itemWriteListener;
    this.errorHandler = errorHandler;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<PendingUsageVoucherActivationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(PendingUsageVoucherActivationListOrder batchOrder) {

    final String stepName = "PendingUsageVoucherActivationListOrderStep";
    JpaItemWriter<ActiveVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader, itemWriter,
        itemProcessor,
        itemWriteListener);
  }

}
