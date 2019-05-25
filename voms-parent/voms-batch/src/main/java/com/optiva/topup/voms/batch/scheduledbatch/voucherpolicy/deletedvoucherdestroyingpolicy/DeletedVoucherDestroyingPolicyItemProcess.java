package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class DeletedVoucherDestroyingPolicyItemProcess implements
    ItemProcessor<DeletedVoucher, DestroyedVoucher> {

  @Override
  public DestroyedVoucher process(DeletedVoucher deletedVoucher) {
    DestroyedVoucher destroyedVoucher = new DestroyedVoucher();
    destroyedVoucher.setSerialNumber(deletedVoucher.getSerialNumber());
    destroyedVoucher.setVoucherId(deletedVoucher.getSerialNumber());
    return destroyedVoucher;
  }

}
