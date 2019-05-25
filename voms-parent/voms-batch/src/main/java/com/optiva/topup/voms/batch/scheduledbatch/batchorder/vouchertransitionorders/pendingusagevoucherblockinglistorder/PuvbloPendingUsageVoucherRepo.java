package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockinglistorder;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuvbloPendingUsageVoucherRepo extends JpaRepository<PendingUsageVoucher, Integer> {

  List<PendingUsageVoucher> findBySerialNumberIn(List<Long> serialNumbers, Pageable pageable);

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
