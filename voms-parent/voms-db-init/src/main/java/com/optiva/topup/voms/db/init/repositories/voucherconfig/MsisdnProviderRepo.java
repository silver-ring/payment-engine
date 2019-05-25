package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsisdnProviderRepo extends JpaRepository<MsisdnProvider, Integer> {

}
