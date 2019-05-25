package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BvaboBlockedVoucherRepo extends JpaRepository<BlockedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
