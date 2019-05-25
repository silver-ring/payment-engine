package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockinglistorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvbloActiveVoucherRepo extends JpaRepository<ActiveVoucher, Integer> {

  List<ActiveVoucher> findBySerialNumberIn(List<Long> serialNumbers, Pageable pageable);

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
