package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationlistorder;

import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationListOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import java.time.LocalDate;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class BlockedVoucherModificationListOrderItemProcessor implements
    ItemProcessor<BlockedVoucher, BlockedVoucher> {

  @Value("#{jobParameters[batchOrder]}")
  private BlockedVoucherModificationListOrder listOrder;

  @Override
  public BlockedVoucher process(BlockedVoucher blockedVoucher) {

    LocalDate expirationDate = listOrder.getExpirationDate();

    if (expirationDate != null) {
      blockedVoucher.setExpirationDate(expirationDate);
    }

    Integer rechargePeriod = listOrder.getRechargePeriod();
    if (rechargePeriod != null) {
      blockedVoucher.setRechargePeriod(rechargePeriod);
    }

    RechargeValue rechargeValue = listOrder.getRechargeValue();
    if (rechargeValue != null) {
      blockedVoucher.setRechargeValue(rechargeValue);
    }

    VoucherType voucherType = listOrder.getVoucherType();
    if (voucherType != null) {
      blockedVoucher.setVoucherType(voucherType);
    }

    return blockedVoucher;
  }

}
