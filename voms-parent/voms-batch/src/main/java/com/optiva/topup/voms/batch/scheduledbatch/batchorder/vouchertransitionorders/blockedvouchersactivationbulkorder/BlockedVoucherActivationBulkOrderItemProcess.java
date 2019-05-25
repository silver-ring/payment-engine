package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class BlockedVoucherActivationBulkOrderItemProcess implements
    ItemProcessor<BlockedVoucher, ActiveVoucher> {

  @Override
  public ActiveVoucher process(BlockedVoucher blockedVoucher) {

    ActiveVoucher activeVoucher = new ActiveVoucher();

    activeVoucher.setVoucherId(blockedVoucher.getVoucherId());
    activeVoucher.setExpirationDate(blockedVoucher.getExpirationDate());
    activeVoucher.setRechargePeriod(blockedVoucher.getRechargePeriod());
    activeVoucher.setRechargeValue(blockedVoucher.getRechargeValue());
    activeVoucher.setVoucherType(blockedVoucher.getVoucherType());
    activeVoucher.setVoucherProvider(blockedVoucher.getVoucherProvider());
    activeVoucher.setSerialNumber(blockedVoucher.getSerialNumber());

    return activeVoucher;
  }

}
