package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VcboCreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

}
