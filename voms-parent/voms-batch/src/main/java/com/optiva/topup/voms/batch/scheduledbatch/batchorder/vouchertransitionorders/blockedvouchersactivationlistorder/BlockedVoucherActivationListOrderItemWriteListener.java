package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationlistorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationListOrderItemWriteListener {

  private final BvaloBlockedVoucherRepo blockedVoucherRepo;

  @Autowired
  public BlockedVoucherActivationListOrderItemWriteListener(BvaloBlockedVoucherRepo blockedVoucherRepo) {
    this.blockedVoucherRepo = blockedVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends ActiveVoucher> activeVouchers) {
    List<Long> serialNumbers = activeVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    blockedVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
