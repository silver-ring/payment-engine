package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PuvUsedVoucherRepo extends SimpleJpaRepository<UsedVoucher, Integer> {

  public PuvUsedVoucherRepo(EntityManager em) {
    super(UsedVoucher.class, em);
  }

}
