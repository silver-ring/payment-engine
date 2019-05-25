package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BLOCKED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherModificationListOrderLauncher extends
    ListOrderJobLauncher<BlockedVoucherModificationListOrder> {

  private final BlockedVoucherModificationListOrderJobListener jobListener;
  private final BlockedVoucherModificationListOrderItemProcessor itemProcessor;
  private final BlockedVoucherModificationListOrderStreamReader streamReader;
  private final BlockedVoucherModificationListOrderErrorHandler errorHandler;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public BlockedVoucherModificationListOrderLauncher(
      BlockedVoucherModificationListOrderJobListener jobListener,
      BlockedVoucherModificationListOrderItemProcessor itemProcessor,
      BlockedVoucherModificationListOrderStreamReader streamReader,
      BlockedVoucherModificationListOrderErrorHandler errorHandler,
      JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.itemProcessor = itemProcessor;
    this.streamReader = streamReader;
    this.errorHandler = errorHandler;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = BLOCKED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<BlockedVoucherModificationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(BlockedVoucherModificationListOrder batchOrder) {

    final String stepName = "BlockedVoucherModificationListOrderStep";

    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, streamReader,
        itemWriter, itemProcessor);

  }

}
