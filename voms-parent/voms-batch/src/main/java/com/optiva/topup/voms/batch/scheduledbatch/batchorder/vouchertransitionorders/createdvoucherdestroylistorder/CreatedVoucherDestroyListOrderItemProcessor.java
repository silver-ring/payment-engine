package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroylistorder;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CreatedVoucherDestroyListOrderItemProcessor implements
    ItemProcessor<CreatedVoucher, DestroyedVoucher> {

  @Override
  public DestroyedVoucher process(CreatedVoucher createdVoucher) {
    DestroyedVoucher destroyedVoucher = new DestroyedVoucher();
    destroyedVoucher.setSerialNumber(createdVoucher.getSerialNumber());
    destroyedVoucher.setVoucherId(createdVoucher.getVoucherId());
    return destroyedVoucher;
  }

}
