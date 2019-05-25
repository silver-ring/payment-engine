package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherTypeRepo extends JpaRepository<VoucherType, Integer> {

}
