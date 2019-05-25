package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationlistorder;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BvaloBlockedVoucherRepo extends JpaRepository<BlockedVoucher, Integer> {

  List<BlockedVoucher> findBySerialNumberIn(List<Long> serialNumbers, Pageable pageable);

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
