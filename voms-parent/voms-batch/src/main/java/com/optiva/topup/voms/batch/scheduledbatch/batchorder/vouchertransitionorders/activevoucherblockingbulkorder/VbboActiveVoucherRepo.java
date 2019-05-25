package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockingbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VbboActiveVoucherRepo extends JpaRepository<ActiveVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
