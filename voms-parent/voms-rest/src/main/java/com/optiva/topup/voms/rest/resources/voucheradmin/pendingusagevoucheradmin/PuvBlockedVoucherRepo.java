package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PuvBlockedVoucherRepo extends SimpleJpaRepository<BlockedVoucher, Integer> {

  public PuvBlockedVoucherRepo(EntityManager em) {
    super(BlockedVoucher.class, em);
  }

}
