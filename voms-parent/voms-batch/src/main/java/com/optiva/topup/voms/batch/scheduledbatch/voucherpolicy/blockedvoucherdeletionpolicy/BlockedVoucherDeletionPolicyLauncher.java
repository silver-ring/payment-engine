package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.blockedvoucherdeletionpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.BLOCKED_VOUCHER_DELETION_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType.BLOCKED_VOUCHER_KEEP_DAYS;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.repositories.configparameters.VoucherPolicyConfigParameterRepo;
import java.time.LocalDate;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherDeletionPolicyLauncher extends VoucherPolicyJobLauncher {

  private final BlockedVoucherDeletionPolicyItemProcess itemProcess;
  private final BlockedVoucherDeletionPolicyItemWriteListener itemWriteListener;
  private final VoucherPolicyConfigParameterRepo configParameterRepo;

  @Autowired
  public BlockedVoucherDeletionPolicyLauncher(
      BlockedVoucherDeletionPolicyItemProcess itemProcess,
      BlockedVoucherDeletionPolicyItemWriteListener itemWriteListener,
      VoucherPolicyConfigParameterRepo configParameterRepo) {
    this.itemProcess = itemProcess;
    this.itemWriteListener = itemWriteListener;
    this.configParameterRepo = configParameterRepo;
  }

  @KafkaListener(topics = BLOCKED_VOUCHER_DELETION_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "BlockedVoucherDeletionPolicyStep";
    final String readerName = "BlockedVoucherDeletionPolicyReader";

    Integer keepDays = Integer
        .parseInt(configParameterRepo.findByParameter(BLOCKED_VOUCHER_KEEP_DAYS).getValue());
    LocalDate keepDate = LocalDate.now().plusDays(keepDays);

    JpaPagingItemReader<BlockedVoucher> itemReader =
        jobBuilderUtils.readExpiredVouchers(readerName, BlockedVoucher.class, keepDate);
    JpaItemWriter<DeletedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

}
