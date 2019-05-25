package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VcboProductionVoucherRepo extends JpaRepository<ProductionVoucher, Long> {

  boolean existsByVoucherId(long voucherId);

}
