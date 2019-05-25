package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationlistorder;

import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationListOrder;
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
public class CreatedVoucherModificationListOrderItemProcessor implements
    ItemProcessor<CreatedVoucher, CreatedVoucher> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherModificationListOrder listOrder;

  @Override
  public CreatedVoucher process(CreatedVoucher createdVoucher) {

    LocalDate expirationDate = listOrder.getExpirationDate();

    if (expirationDate != null) {
      createdVoucher.setExpirationDate(expirationDate);
    }

    Integer rechargePeriod = listOrder.getRechargePeriod();
    if (rechargePeriod != null) {
      createdVoucher.setRechargePeriod(rechargePeriod);
    }

    RechargeValue rechargeValue = listOrder.getRechargeValue();
    if (rechargeValue != null) {
      createdVoucher.setRechargeValue(rechargeValue);
    }

    VoucherType voucherType = listOrder.getVoucherType();
    if (voucherType != null) {
      createdVoucher.setVoucherType(voucherType);
    }

    return createdVoucher;
  }

}
