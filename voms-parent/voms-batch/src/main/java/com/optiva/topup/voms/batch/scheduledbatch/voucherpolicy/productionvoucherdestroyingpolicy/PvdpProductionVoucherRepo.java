package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.productionvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PvdpProductionVoucherRepo extends JpaRepository<ProductionVoucher, Integer> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
