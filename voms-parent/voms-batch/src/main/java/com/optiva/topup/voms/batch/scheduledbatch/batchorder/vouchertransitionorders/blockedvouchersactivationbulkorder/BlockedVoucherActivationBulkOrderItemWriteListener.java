package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationBulkOrderItemWriteListener {

  private final BvaboBlockedVoucherRepo vaoActiveVoucherRepo;

  @Autowired
  public BlockedVoucherActivationBulkOrderItemWriteListener(BvaboBlockedVoucherRepo vaoActiveVoucherRepo) {
    this.vaoActiveVoucherRepo = vaoActiveVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends ActiveVoucher> activeVouchers) {
    List<Long> serialNumbers = activeVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    vaoActiveVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
