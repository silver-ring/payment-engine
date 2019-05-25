package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.createdvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvdpCreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
