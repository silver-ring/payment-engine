package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.OcsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcsAccountRepo extends JpaRepository<OcsAccount, Integer> {

}
