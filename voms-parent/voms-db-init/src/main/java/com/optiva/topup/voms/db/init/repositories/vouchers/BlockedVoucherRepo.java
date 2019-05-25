package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedVoucherRepo extends JpaRepository<BlockedVoucher, Integer> {

}
