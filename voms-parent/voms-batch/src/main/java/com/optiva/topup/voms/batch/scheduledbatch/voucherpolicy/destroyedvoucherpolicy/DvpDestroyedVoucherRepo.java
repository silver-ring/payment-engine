package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.destroyedvoucherpolicy;

import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DvpDestroyedVoucherRepo extends JpaRepository<DestroyedVoucher, Integer> {

}
