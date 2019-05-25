package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusingbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherUsingBulkOrderItemWriteListener {

  private final PuvuboPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public PendingUsageVoucherUsingBulkOrderItemWriteListener(
      PuvuboPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends UsedVoucher> usedVouchers) {
    List<Long> serialNumbers = usedVouchers.stream().map(Voucher::getSerialNumber)
        .collect(Collectors.toList());
    pendingUsageVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
