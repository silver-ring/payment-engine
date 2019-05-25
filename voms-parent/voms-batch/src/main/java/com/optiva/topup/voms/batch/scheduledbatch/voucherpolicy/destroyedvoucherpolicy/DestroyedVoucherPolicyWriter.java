package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.destroyedvoucherpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DestroyedVoucherPolicyWriter implements ItemWriter<DestroyedVoucher> {

  private final DvpDestroyedVoucherRepo destroyedVoucherRepo;
  private final DvpVoucherHistoryRepo voucherHistoryRepo;

  @Autowired
  public DestroyedVoucherPolicyWriter(DvpDestroyedVoucherRepo destroyedVoucherRepo,
      DvpVoucherHistoryRepo voucherHistoryRepo) {
    this.destroyedVoucherRepo = destroyedVoucherRepo;
    this.voucherHistoryRepo = voucherHistoryRepo;
  }

  @Override
  public void write(List<? extends DestroyedVoucher> destroyedVouchers) {
    List<Long> serialNumbers = destroyedVouchers.stream()
        .map(Voucher::getSerialNumber).collect(Collectors.toList());
    destroyedVoucherRepo.deleteAll(destroyedVouchers);
    voucherHistoryRepo.deleteBySerialNumberIn(serialNumbers);
  }
}
