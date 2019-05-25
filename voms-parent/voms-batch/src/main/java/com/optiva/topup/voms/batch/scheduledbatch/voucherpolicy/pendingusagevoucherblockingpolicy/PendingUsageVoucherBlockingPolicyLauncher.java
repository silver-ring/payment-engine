package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.pendingusagevoucherblockingpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.PENDING_USAGE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingPolicyLauncher extends VoucherPolicyJobLauncher {

  private final PendingUsageVoucherBlockingPolicyItemProcess itemProcess;
  private final PendingUsageVoucherBlockingPolicyItemWriteListener
      itemWriteListener;

  @Autowired
  public PendingUsageVoucherBlockingPolicyLauncher(
      PendingUsageVoucherBlockingPolicyItemProcess itemProcess,
      PendingUsageVoucherBlockingPolicyItemWriteListener itemWriteListener) {
    this.itemProcess = itemProcess;
    this.itemWriteListener =
        itemWriteListener;
  }

  @KafkaListener(topics = PENDING_USAGE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "PendingUsageVoucherBlockingPolicyStep";
    final String readerName = "PendingUsageVoucherBlockingPolicyReader";

    JpaPagingItemReader<PendingUsageVoucher> itemReader =
        jobBuilderUtils.readExpiredVouchers(readerName, PendingUsageVoucher.class);
    JpaItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);

  }

}
