package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RMsisdnProviderRepo extends JpaRepository<MsisdnProvider, Integer> {

  @Query("select mp from MsisdnProvider mp where mp.idAtIn=:idAtIn")
  MsisdnProvider findByIdAtIn(@Param("idAtIn") Integer idAtIn);

}
