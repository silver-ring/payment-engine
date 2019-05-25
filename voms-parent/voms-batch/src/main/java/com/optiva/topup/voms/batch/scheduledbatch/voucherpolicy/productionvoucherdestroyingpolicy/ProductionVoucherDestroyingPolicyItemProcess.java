package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.productionvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ProductionVoucherDestroyingPolicyItemProcess implements
    ItemProcessor<ProductionVoucher, DestroyedVoucher> {

  @Override
  public DestroyedVoucher process(ProductionVoucher productionVoucher) {
    DestroyedVoucher destroyedVoucher = new DestroyedVoucher();
    destroyedVoucher.setSerialNumber(productionVoucher.getSerialNumber());
    destroyedVoucher.setVoucherId(productionVoucher.getSerialNumber());
    return destroyedVoucher;
  }

}
