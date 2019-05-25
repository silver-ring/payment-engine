package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.voucherconfig.ConsumableVoucherType;
import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RConsumableVoucherTypeRepo extends JpaRepository<ConsumableVoucherType, Integer> {

  boolean existsByMsisdnProviderAndVoucherProviderAndVoucherType(MsisdnProvider msisdnProvider,
      VoucherProvider voucherProvider, VoucherType voucherType);

}
