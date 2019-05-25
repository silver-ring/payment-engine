package com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AvActiveVoucherRepo extends SimpleJpaRepository<ActiveVoucher, Integer> {

  public AvActiveVoucherRepo(EntityManager em) {
    super(ActiveVoucher.class, em);
  }
}
