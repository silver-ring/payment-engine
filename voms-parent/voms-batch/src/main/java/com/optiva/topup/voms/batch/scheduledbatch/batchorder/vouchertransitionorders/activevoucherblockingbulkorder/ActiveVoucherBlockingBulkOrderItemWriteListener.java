package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockingbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingBulkOrderItemWriteListener {

  private final VbboActiveVoucherRepo activeVoucherRepo;

  @Autowired
  public ActiveVoucherBlockingBulkOrderItemWriteListener(VbboActiveVoucherRepo activeVoucherRepo) {
    this.activeVoucherRepo = activeVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends BlockedVoucher> activeVouchers) {
    List<Long> serialNumbers = activeVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    activeVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
