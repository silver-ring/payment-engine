package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusingbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PendingUsageVoucherUsingBulkOrderItemProcess implements
    ItemProcessor<PendingUsageVoucher, UsedVoucher> {

  @Override
  public UsedVoucher process(PendingUsageVoucher pendingUsageVoucher) {
    UsedVoucher usedVoucher = new UsedVoucher();
    usedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    usedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    usedVoucher.setFinalRechargeValue(pendingUsageVoucher.getFinalRechargeValue());
    usedVoucher.setMsisdn(pendingUsageVoucher.getMsisdn());
    usedVoucher.setCcid(pendingUsageVoucher.getCcid());
    return usedVoucher;
  }

}
