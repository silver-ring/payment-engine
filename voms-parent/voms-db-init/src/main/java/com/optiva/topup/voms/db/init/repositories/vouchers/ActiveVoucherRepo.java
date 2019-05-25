package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveVoucherRepo extends JpaRepository<ActiveVoucher, Long> {

}
