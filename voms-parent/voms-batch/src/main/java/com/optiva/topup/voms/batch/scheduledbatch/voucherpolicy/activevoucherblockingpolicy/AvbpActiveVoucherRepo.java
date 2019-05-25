package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.activevoucherblockingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvbpActiveVoucherRepo extends JpaRepository<ActiveVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
