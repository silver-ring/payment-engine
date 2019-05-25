package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationlistorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationListOrderItemWriteListener {

  private final PuvaloPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public PendingUsageVoucherActivationListOrderItemWriteListener(
      PuvaloPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends ActiveVoucher> destroyedVouchers) {
    List<Long> serialNumbers = destroyedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    pendingUsageVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
