package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BohVoucherProviderRepo extends SimpleJpaRepository<VoucherProvider, Integer> {

  public BohVoucherProviderRepo(EntityManager em) {
    super(VoucherProvider.class, em);
  }

}
