package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatedVoucherRepo extends JpaRepository<CreatedVoucher, Integer> {

  boolean existsByVoucherId(Long voucherId);
}
