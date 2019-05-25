package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DvdpDeletedVoucherRepo extends JpaRepository<DeletedVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
