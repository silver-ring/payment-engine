package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.usedvoucherdeletionpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.USED_VOUCHER_DELETION_POLICY_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UsedVoucherDeletionPolicyLauncher extends VoucherPolicyJobLauncher {

  private final UsedVoucherDeletionPolicyItemProcess itemProcess;
  private final UsedVoucherDeletionPolicyItemWriteListener itemWriteListener;

  @Autowired
  public UsedVoucherDeletionPolicyLauncher(
      UsedVoucherDeletionPolicyItemProcess itemProcess,
      UsedVoucherDeletionPolicyItemWriteListener itemWriteListener) {
    this.itemProcess = itemProcess;
    this.itemWriteListener = itemWriteListener;
  }

  @KafkaListener(topics = USED_VOUCHER_DELETION_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "UsedVoucherDeletionPolicyStep";
    final String readerName = "UsedVoucherDeletionPolicyReader";

    JpaPagingItemReader<UsedVoucher> itemReader =
        jobBuilderUtils.readAllVouchers(readerName, UsedVoucher.class);
    JpaItemWriter<DeletedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

}
