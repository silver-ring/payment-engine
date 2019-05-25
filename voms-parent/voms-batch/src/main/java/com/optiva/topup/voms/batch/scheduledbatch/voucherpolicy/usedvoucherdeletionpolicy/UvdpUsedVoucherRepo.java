package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.usedvoucherdeletionpolicy;

import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UvdpUsedVoucherRepo extends JpaRepository<UsedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
