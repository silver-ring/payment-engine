package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationBulkOrderItemWriteListener {

  private final PuvaboPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public PendingUsageVoucherActivationBulkOrderItemWriteListener(
      PuvaboPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends ActiveVoucher> activeVouchers) {
    List<Long> serialNumbers = activeVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    pendingUsageVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
