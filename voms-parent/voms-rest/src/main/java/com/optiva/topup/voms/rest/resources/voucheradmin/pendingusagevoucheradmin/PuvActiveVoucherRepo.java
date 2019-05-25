package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PuvActiveVoucherRepo extends SimpleJpaRepository<ActiveVoucher, Integer> {

  public PuvActiveVoucherRepo(EntityManager em) {
    super(ActiveVoucher.class, em);
  }

}
