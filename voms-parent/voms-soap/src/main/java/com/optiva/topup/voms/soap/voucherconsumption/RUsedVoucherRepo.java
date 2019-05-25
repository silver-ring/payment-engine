package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RUsedVoucherRepo extends JpaRepository<UsedVoucher, Long> {

}
