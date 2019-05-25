package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.VoucherCreationBatchOrder;
import com.optiva.topup.voms.common.repositories.VomsGeneratorRepo;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class VoucherCreationBatchOrderJobListener extends BatchOrderJobListener<VoucherCreationBatchOrder> {

  private final VomsGeneratorRepo vomsGeneratorsRepo;

  @Value("#{jobParameters[batchOrder]}")
  private VoucherCreationBatchOrder voucherCreationBatchOrder;
  @Value("#{jobParameters[startSerialNumber]}")
  private Long startSerialNumber;

  @Autowired
  public VoucherCreationBatchOrderJobListener(VomsGeneratorRepo vomsGeneratorsRepo) {
    this.vomsGeneratorsRepo = vomsGeneratorsRepo;
  }

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(startSerialNumber);
    batchOrderHistory.setNumberOfVouchers(voucherCreationBatchOrder.getNumberOfVouchers());
    batchOrderHistory.setExpirationDate(voucherCreationBatchOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(voucherCreationBatchOrder.getRechargePeriod());
    batchOrderHistory.setRechargeValueId(voucherCreationBatchOrder.getRechargeValue().getId());
    batchOrderHistory.setVoucherTypeId(voucherCreationBatchOrder.getVoucherType().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.VOUCHER_CREATION_BATCH_ORDER);

    if (batchOrderHistory.getBatchJobHistoryStatus() != BatchJobHistoryStatus.STARTED) {
      batchOrderHistory.setEndSerialNumber(getEndSerialNumber());
    }

    return batchOrderHistory;
  }

  private Long getEndSerialNumber() {
    return vomsGeneratorsRepo.voucherSerialNumber().getValue() - 1;
  }

}
