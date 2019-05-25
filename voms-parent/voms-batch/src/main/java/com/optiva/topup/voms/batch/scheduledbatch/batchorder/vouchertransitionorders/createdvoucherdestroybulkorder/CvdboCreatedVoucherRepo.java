package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroybulkorder;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvdboCreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
