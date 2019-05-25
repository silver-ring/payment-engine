package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationbulkorder;

import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationBulkOrder;
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
public class BlockedVoucherModificationBulkOrderItemProcessor implements
    ItemProcessor<BlockedVoucher, BlockedVoucher> {

  @Value("#{jobParameters[batchOrder]}")
  private BlockedVoucherModificationBulkOrder bulkOrder;

  @Override
  public BlockedVoucher process(BlockedVoucher blockedVoucher) {

    LocalDate expirationDate = bulkOrder.getExpirationDate();

    if (expirationDate != null) {
      blockedVoucher.setExpirationDate(expirationDate);
    }

    Integer rechargePeriod = bulkOrder.getRechargePeriod();
    if (rechargePeriod != null) {
      blockedVoucher.setRechargePeriod(rechargePeriod);
    }

    RechargeValue rechargeValue = bulkOrder.getRechargeValue();
    if (rechargeValue != null) {
      blockedVoucher.setRechargeValue(rechargeValue);
    }

    VoucherType voucherType = bulkOrder.getVoucherType();
    if (voucherType != null) {
      blockedVoucher.setVoucherType(voucherType);
    }

    return blockedVoucher;
  }

}
