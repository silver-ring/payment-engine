package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockinglistorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ActiveVoucherBlockingListOrderItemProcessor implements
    ItemProcessor<ActiveVoucher, BlockedVoucher> {

  @Override
  public BlockedVoucher process(ActiveVoucher activeVoucher) {

    BlockedVoucher blockedVoucher = new BlockedVoucher();
    blockedVoucher.setVoucherId(activeVoucher.getVoucherId());
    blockedVoucher.setExpirationDate(activeVoucher.getExpirationDate());
    blockedVoucher.setRechargePeriod(activeVoucher.getRechargePeriod());
    blockedVoucher.setRechargeValue(activeVoucher.getRechargeValue());
    blockedVoucher.setVoucherType(activeVoucher.getVoucherType());
    blockedVoucher.setVoucherProvider(activeVoucher.getVoucherProvider());
    blockedVoucher.setSerialNumber(activeVoucher.getSerialNumber());

    return blockedVoucher;
  }

}
