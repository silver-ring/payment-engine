package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.productionvoucherdestroyingpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.PRODUCTION_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType.PRODUCTION_VOUCHER_KEEP_DAYS;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import com.optiva.topup.voms.common.repositories.configparameters.VoucherPolicyConfigParameterRepo;
import java.time.LocalDate;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductionVoucherDestroyingPolicyLauncher extends VoucherPolicyJobLauncher {

  private final ProductionVoucherDestroyingPolicyItemWriteListener
      itemWriteListener;
  private final ProductionVoucherDestroyingPolicyItemProcess itemProcess;
  private final VoucherPolicyConfigParameterRepo configParameterRepo;

  @Autowired
  public ProductionVoucherDestroyingPolicyLauncher(
      ProductionVoucherDestroyingPolicyItemWriteListener itemWriteListener,
      ProductionVoucherDestroyingPolicyItemProcess itemProcess,
      VoucherPolicyConfigParameterRepo configParameterRepo) {
    this.itemWriteListener =
        itemWriteListener;
    this.itemProcess = itemProcess;
    this.configParameterRepo = configParameterRepo;
  }

  @KafkaListener(topics = PRODUCTION_VOUCHER_DESTROYING_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepName = "ProductionVoucherDestroyingPolicyStep";
    final String readerName = "ProductionVoucherDestroyingPolicyReader";

    Integer keepDays = Integer
        .parseInt(configParameterRepo.findByParameter(PRODUCTION_VOUCHER_KEEP_DAYS).getValue());
    LocalDate keepDate = LocalDate.now().plusDays(keepDays);

    JpaPagingItemReader<ProductionVoucher> itemReader =
        jobBuilderUtils.readExpiredVouchers(readerName, ProductionVoucher.class, keepDate);
    JpaItemWriter<DestroyedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

}
