package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedVoucherRepo extends JpaRepository<UsedVoucher, Integer> {

}
