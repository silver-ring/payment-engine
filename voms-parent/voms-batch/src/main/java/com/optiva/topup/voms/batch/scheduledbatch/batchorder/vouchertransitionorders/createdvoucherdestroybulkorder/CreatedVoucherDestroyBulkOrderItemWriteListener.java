package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroybulkorder;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyBulkOrderItemWriteListener {

  private final CvdboCreatedVoucherRepo createdVoucherRepo;

  @Autowired
  public CreatedVoucherDestroyBulkOrderItemWriteListener(CvdboCreatedVoucherRepo createdVoucherRepo) {
    this.createdVoucherRepo = createdVoucherRepo;
  }

  @AfterWrite
  public void afterWrite(List<? extends DestroyedVoucher> destroyedVouchers) {
    List<Long> serialNumbers = destroyedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    createdVoucherRepo.deleteBySerialNumberIn(serialNumbers);
  }

}
