package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.createdvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyingPolicyItemWriteListener {

  private final CvdpCreatedVoucherRepo createdVoucherRepo;

  @Autowired
  public CreatedVoucherDestroyingPolicyItemWriteListener(CvdpCreatedVoucherRepo createdVoucherRepo) {
    this.createdVoucherRepo = createdVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends DestroyedVoucher> destroyedVouchers) {
    List<Long> serialNumbers = destroyedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    createdVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
