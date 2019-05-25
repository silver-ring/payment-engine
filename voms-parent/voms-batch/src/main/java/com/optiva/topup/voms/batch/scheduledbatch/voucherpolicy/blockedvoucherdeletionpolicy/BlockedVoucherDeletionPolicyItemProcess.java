package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.blockedvoucherdeletionpolicy;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class BlockedVoucherDeletionPolicyItemProcess implements
    ItemProcessor<BlockedVoucher, DeletedVoucher> {

  @Override
  public DeletedVoucher process(BlockedVoucher blockedVoucher) {
    DeletedVoucher deletedVoucher = new DeletedVoucher();
    deletedVoucher.setSerialNumber(blockedVoucher.getSerialNumber());
    deletedVoucher.setVoucherId(blockedVoucher.getSerialNumber());
    return deletedVoucher;
  }

}
