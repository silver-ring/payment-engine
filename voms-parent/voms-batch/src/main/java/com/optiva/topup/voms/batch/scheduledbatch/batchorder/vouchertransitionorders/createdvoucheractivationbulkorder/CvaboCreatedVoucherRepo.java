package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationbulkorder;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvaboCreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);
}
