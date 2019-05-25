package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuvaboPendingUsageVoucherRepo extends JpaRepository<PendingUsageVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);
}
