package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.Taxation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxationRepo extends JpaRepository<Taxation, Integer> {

}
