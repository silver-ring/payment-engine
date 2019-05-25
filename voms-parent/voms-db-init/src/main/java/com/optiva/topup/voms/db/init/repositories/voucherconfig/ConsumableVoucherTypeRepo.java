package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.ConsumableVoucherType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumableVoucherTypeRepo extends JpaRepository<ConsumableVoucherType, Integer> {

}
