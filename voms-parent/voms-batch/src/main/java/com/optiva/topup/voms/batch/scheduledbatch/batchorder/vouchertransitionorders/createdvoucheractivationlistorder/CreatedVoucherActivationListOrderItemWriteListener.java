package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationlistorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherActivationListOrderItemWriteListener {

  private final CvaloCreatedVoucherRepo createdVoucherRepo;

  @Autowired
  public CreatedVoucherActivationListOrderItemWriteListener(CvaloCreatedVoucherRepo createdVoucherRepo) {
    this.createdVoucherRepo = createdVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends ActiveVoucher> activeVouchers) {
    List<Long> serialNumbers = activeVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    createdVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
