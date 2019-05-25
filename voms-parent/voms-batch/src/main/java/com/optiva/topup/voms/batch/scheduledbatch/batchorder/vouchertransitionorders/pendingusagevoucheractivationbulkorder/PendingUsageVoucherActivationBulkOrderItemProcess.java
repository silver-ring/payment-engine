package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PendingUsageVoucherActivationBulkOrderItemProcess implements
    ItemProcessor<PendingUsageVoucher, ActiveVoucher> {

  @Override
  public ActiveVoucher process(PendingUsageVoucher pendingUsageVoucher) {
    ActiveVoucher activeVoucher = new ActiveVoucher();
    activeVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    activeVoucher.setExpirationDate(pendingUsageVoucher.getExpirationDate());
    activeVoucher.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
    activeVoucher.setRechargeValue(pendingUsageVoucher.getRechargeValue());
    activeVoucher.setVoucherType(pendingUsageVoucher.getVoucherType());
    activeVoucher.setVoucherProvider(pendingUsageVoucher.getVoucherProvider());
    activeVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    return activeVoucher;
  }

}
