package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationlistorder;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuvaloPendingUsageVoucherRepo extends JpaRepository<PendingUsageVoucher, Integer> {

  List<PendingUsageVoucher> findBySerialNumberIn(List<Long> serialNumbers, Pageable pageable);

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
