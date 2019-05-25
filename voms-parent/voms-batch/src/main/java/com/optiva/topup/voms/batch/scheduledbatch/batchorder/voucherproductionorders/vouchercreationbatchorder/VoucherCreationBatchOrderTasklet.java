package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.VoucherCreationBatchOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VomsGenerator;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import com.optiva.topup.voms.common.repositories.VomsGeneratorRepo;
import java.security.SecureRandom;
import java.time.LocalDate;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class VoucherCreationBatchOrderTasklet implements Tasklet {

  private final VcboCreatedVoucherRepo createdVoucherRepo;
  private final VomsGeneratorRepo vomsGeneratorRepo;
  private final VcboProductionVoucherRepo productionVoucherRepo;
  @Value("#{jobParameters[batchOrder]}")
  private VoucherCreationBatchOrder voucherCreationBatchOrder;
  @Value("#{jobParameters[voucherIdLength]}")
  private Long voucherIdLength;

  @Autowired
  public VoucherCreationBatchOrderTasklet(VcboCreatedVoucherRepo createdVoucherRepo,
      VomsGeneratorRepo vomsGeneratorRepo, VcboProductionVoucherRepo productionVoucherRepo) {
    this.createdVoucherRepo = createdVoucherRepo;
    this.vomsGeneratorRepo = vomsGeneratorRepo;
    this.productionVoucherRepo = productionVoucherRepo;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

    Integer numberOfVouchers = voucherCreationBatchOrder.getNumberOfVouchers();

    if (chunkContext.getStepContext().getStepExecution().getCommitCount() == numberOfVouchers) {
      return RepeatStatus.FINISHED;
    }

    final Integer rechargePeriod = voucherCreationBatchOrder.getRechargePeriod();
    final RechargeValue rechargeValue = voucherCreationBatchOrder.getRechargeValue();
    final VoucherType voucherType = voucherCreationBatchOrder.getVoucherType();
    final LocalDate expirationDate = voucherCreationBatchOrder.getExpirationDate();

    final long voucherId = generateVoucherId();

    VomsGenerator voucherSerialNumber = getVoucherSerialNumber();
    final Long currentValue = voucherSerialNumber.getValue();
    Long nextValue = 1 + voucherSerialNumber.getValue();
    voucherSerialNumber.setValue(nextValue);

    ProductionVoucher productionVoucher = new ProductionVoucher();
    productionVoucher.setSerialNumber(voucherSerialNumber.getValue());
    productionVoucher.setExpirationDate(expirationDate);
    productionVoucher.setVoucherId(voucherId);
    productionVoucher.setRechargePeriod(rechargePeriod);

    CreatedVoucher createdVoucher = new CreatedVoucher();
    createdVoucher.setVoucherId(voucherId);
    createdVoucher.setExpirationDate(expirationDate);
    createdVoucher.setSerialNumber(currentValue);
    createdVoucher.setRechargeValue(rechargeValue);
    createdVoucher.setVoucherType(voucherType);
    createdVoucher.setRechargePeriod(rechargePeriod);

    productionVoucherRepo.save(productionVoucher);
    createdVoucherRepo.save(createdVoucher);
    vomsGeneratorRepo.save(voucherSerialNumber);
    return RepeatStatus.CONTINUABLE;
  }

  private long generateVoucherId() {

    SecureRandom rng = new SecureRandom();
    long max = (long) (Math.pow(10, voucherIdLength) - 1);
    long offset = (long) Math.pow(10, voucherIdLength - 1);
    long available = max - offset;
    long voucherId;
    do {
      double rndDouble = rng.nextDouble();
      voucherId = (long) (rndDouble * available) + offset;
    } while (voucherId < 0 || productionVoucherRepo
        .existsByVoucherId(voucherId)); // make sure the result is positive

    return voucherId;
  }

  private VomsGenerator getVoucherSerialNumber() {
    return vomsGeneratorRepo.voucherSerialNumber();
  }

}
