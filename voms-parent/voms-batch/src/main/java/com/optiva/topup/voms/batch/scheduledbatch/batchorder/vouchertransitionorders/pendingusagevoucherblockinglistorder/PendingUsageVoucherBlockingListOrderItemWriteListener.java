package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockinglistorder;

import com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationlistorder.PuvaloPendingUsageVoucherRepo;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingListOrderItemWriteListener {

  private final PuvaloPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public PendingUsageVoucherBlockingListOrderItemWriteListener(
      PuvaloPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends BlockedVoucher> blockedVouchers) {
    List<Long> serialNumbers = blockedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    pendingUsageVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
