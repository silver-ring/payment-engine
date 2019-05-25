package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VhVoucherProviderRepo extends SimpleJpaRepository<VoucherProvider, Integer> {

  public VhVoucherProviderRepo(EntityManager em) {
    super(VoucherProvider.class, em);
  }

}
