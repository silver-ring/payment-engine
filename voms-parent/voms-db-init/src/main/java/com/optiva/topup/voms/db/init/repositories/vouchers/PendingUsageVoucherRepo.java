package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingUsageVoucherRepo extends JpaRepository<PendingUsageVoucher, Integer> {

}
