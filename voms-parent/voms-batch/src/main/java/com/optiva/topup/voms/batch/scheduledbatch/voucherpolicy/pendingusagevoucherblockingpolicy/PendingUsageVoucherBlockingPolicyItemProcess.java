package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.pendingusagevoucherblockingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PendingUsageVoucherBlockingPolicyItemProcess implements
    ItemProcessor<PendingUsageVoucher, BlockedVoucher> {

  @Override
  public BlockedVoucher process(PendingUsageVoucher pendingUsageVoucher) {

    BlockedVoucher blockedVoucher = new BlockedVoucher();

    blockedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    blockedVoucher.setExpirationDate(pendingUsageVoucher.getExpirationDate());
    blockedVoucher.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
    blockedVoucher.setRechargeValue(pendingUsageVoucher.getRechargeValue());
    blockedVoucher.setVoucherType(pendingUsageVoucher.getVoucherType());
    blockedVoucher.setVoucherProvider(pendingUsageVoucher.getVoucherProvider());
    blockedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());

    return blockedVoucher;
  }

}
