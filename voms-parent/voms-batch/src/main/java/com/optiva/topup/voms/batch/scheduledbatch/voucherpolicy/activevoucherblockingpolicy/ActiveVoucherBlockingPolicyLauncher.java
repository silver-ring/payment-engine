package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.activevoucherblockingpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.ACTIVE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingPolicyLauncher extends VoucherPolicyJobLauncher {

  private final ActiveVoucherBlockingPolicyItemProcess itemProcess;
  private final ActiveVoucherBlockingPolicyItemWriteListener itemWriteListener;

  @Autowired
  public ActiveVoucherBlockingPolicyLauncher(
      ActiveVoucherBlockingPolicyItemProcess itemProcess,
      ActiveVoucherBlockingPolicyItemWriteListener itemWriteListener) {
    this.itemProcess = itemProcess;
    this.itemWriteListener = itemWriteListener;
  }

  @KafkaListener(topics = ACTIVE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC)
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "ActiveVoucherBlockingPolicyStep";
    final String readerName = "ActiveVoucherBlockingPolicyReader";

    JpaPagingItemReader<ActiveVoucher> itemReader =
        jobBuilderUtils.readExpiredVouchers(readerName, ActiveVoucher.class);
    ItemWriter<BlockedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

}
