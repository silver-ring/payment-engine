package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.destroyedvoucherpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.DESTROYED_VOUCHER_POLICY_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DestroyedVoucherPolicyLauncher extends VoucherPolicyJobLauncher {

  private final DestroyedVoucherPolicyWriter destroyedVoucherPolicyWriter;

  @Autowired
  public DestroyedVoucherPolicyLauncher(DestroyedVoucherPolicyWriter destroyedVoucherPolicyWriter) {
    this.destroyedVoucherPolicyWriter = destroyedVoucherPolicyWriter;
  }

  @KafkaListener(topics = DESTROYED_VOUCHER_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {
    final String stepName = "UsedVoucherDeletionPolicyStep";
    final String readerName = "UsedVoucherDeletionPolicyReader";
    JpaPagingItemReader<DestroyedVoucher> itemReader =
        jobBuilderUtils.readAllVouchers(readerName, DestroyedVoucher.class);
    return jobBuilderUtils.buildStep(stepName, itemReader, destroyedVoucherPolicyWriter);
  }

}
