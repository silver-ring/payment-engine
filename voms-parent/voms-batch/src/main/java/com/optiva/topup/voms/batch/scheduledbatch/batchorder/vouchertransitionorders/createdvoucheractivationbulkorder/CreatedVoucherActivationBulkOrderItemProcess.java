package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationbulkorder;

import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CreatedVoucherActivationBulkOrderItemProcess implements
    ItemProcessor<CreatedVoucher, ActiveVoucher> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherActivationBulkOrder bulkOrder;

  @Override
  public ActiveVoucher process(CreatedVoucher createdVoucher) {

    ActiveVoucher activeVoucher = new ActiveVoucher();

    activeVoucher.setExpirationDate(createdVoucher.getExpirationDate());
    activeVoucher.setRechargePeriod(createdVoucher.getRechargePeriod());
    activeVoucher.setRechargeValue(createdVoucher.getRechargeValue());
    activeVoucher.setSerialNumber(createdVoucher.getSerialNumber());
    activeVoucher.setVoucherId(createdVoucher.getVoucherId());
    activeVoucher.setVoucherType(createdVoucher.getVoucherType());
    activeVoucher.setVoucherProvider(bulkOrder.getVoucherProvider());

    return activeVoucher;
  }
}
