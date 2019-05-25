package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.pendingusagevoucherblockingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingPolicyItemWriteListener {

  private final PuvbpPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public PendingUsageVoucherBlockingPolicyItemWriteListener(
      PuvbpPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends BlockedVoucher> blockedVouchers) {
    List<Long> serialNumbers = blockedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    pendingUsageVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
