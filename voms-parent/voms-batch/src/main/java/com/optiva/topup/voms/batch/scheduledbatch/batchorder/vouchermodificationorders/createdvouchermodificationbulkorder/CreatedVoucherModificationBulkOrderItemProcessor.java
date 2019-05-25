package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationbulkorder;

import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.time.LocalDate;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CreatedVoucherModificationBulkOrderItemProcessor implements
    ItemProcessor<CreatedVoucher, CreatedVoucher> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherModificationBulkOrder bulkOrder;

  @Override
  public CreatedVoucher process(CreatedVoucher createdVoucher) {

    LocalDate expirationDate = bulkOrder.getExpirationDate();

    if (expirationDate != null) {
      createdVoucher.setExpirationDate(expirationDate);
    }

    Integer rechargePeriod = bulkOrder.getRechargePeriod();
    if (rechargePeriod != null) {
      createdVoucher.setRechargePeriod(rechargePeriod);
    }

    RechargeValue rechargeValue = bulkOrder.getRechargeValue();
    if (rechargeValue != null) {
      createdVoucher.setRechargeValue(rechargeValue);
    }

    VoucherType voucherType = bulkOrder.getVoucherType();
    if (voucherType != null) {
      createdVoucher.setVoucherType(voucherType);
    }

    return createdVoucher;
  }

}
