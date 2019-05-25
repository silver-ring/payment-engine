package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.blockedvoucherdeletionpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherDeletionPolicyItemWriteListener {

  private final BvdpBlockedVoucherRepo blockedVoucherRepo;

  @Autowired
  public BlockedVoucherDeletionPolicyItemWriteListener(BvdpBlockedVoucherRepo blockedVoucherRepo) {
    this.blockedVoucherRepo = blockedVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends DeletedVoucher> deletedVouchers) {
    List<Long> serialNumbers = deletedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    blockedVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
