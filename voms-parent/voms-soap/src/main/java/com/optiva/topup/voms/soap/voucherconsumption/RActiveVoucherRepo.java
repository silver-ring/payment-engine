package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RActiveVoucherRepo extends JpaRepository<ActiveVoucher, Long> {

  ActiveVoucher findByVoucherId(Long voucherId);

}
