package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.createdvoucherdestroyingpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.CREATED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyingPolicyLauncher extends VoucherPolicyJobLauncher {

  private final CreatedVoucherDestroyingPolicyItemProcess itemProcess;
  private final CreatedVoucherDestroyingPolicyItemWriteListener itemWriteListener;

  @Autowired
  public CreatedVoucherDestroyingPolicyLauncher(
      CreatedVoucherDestroyingPolicyItemProcess itemProcess,
      CreatedVoucherDestroyingPolicyItemWriteListener itemWriteListener) {
    this.itemProcess = itemProcess;
    this.itemWriteListener = itemWriteListener;
  }

  @KafkaListener(topics = CREATED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "CreatedVoucherDestroyingPolicyStep";
    final String readerName = "CreatedVoucherDestroyingPolicyReader";

    JpaPagingItemReader<CreatedVoucher> itemReader =
        jobBuilderUtils.readExpiredVouchers(readerName, CreatedVoucher.class);
    ItemWriter<DestroyedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

}
