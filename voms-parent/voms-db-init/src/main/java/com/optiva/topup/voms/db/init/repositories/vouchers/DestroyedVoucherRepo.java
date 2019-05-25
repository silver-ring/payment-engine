package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestroyedVoucherRepo extends JpaRepository<DestroyedVoucher, Integer> {

}
