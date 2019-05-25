package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PuvPendingUsageVoucherRepo extends SimpleJpaRepository<PendingUsageVoucher, Integer> {

  public PuvPendingUsageVoucherRepo(EntityManager em) {
    super(PendingUsageVoucher.class, em);
  }

}
