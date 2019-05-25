package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RPendingUsageVoucherRepo extends JpaRepository<PendingUsageVoucher, Long> {

  Optional<PendingUsageVoucher> findByVoucherId(Long voucherId);

}
