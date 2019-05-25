package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroylistorder;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvdloCreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

  List<CreatedVoucher> findBySerialNumberIn(List<Long> serialNumbers, Pageable pageable);

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
