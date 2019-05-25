package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.usedvoucherdeletionpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class UsedVoucherDeletionPolicyItemProcess implements ItemProcessor<UsedVoucher, DeletedVoucher> {

  @Override
  public DeletedVoucher process(UsedVoucher usedVoucher) {
    DeletedVoucher deletedVoucher = new DeletedVoucher();
    deletedVoucher.setSerialNumber(usedVoucher.getSerialNumber());
    deletedVoucher.setVoucherId(usedVoucher.getSerialNumber());
    return deletedVoucher;
  }

}
