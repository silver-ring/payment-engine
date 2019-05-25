package com.optiva.topup.voms.rest.resources.voucheradmin.blockedvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BvBlockedVoucherRepo extends SimpleJpaRepository<BlockedVoucher, Integer> {

  public BvBlockedVoucherRepo(EntityManager em) {
    super(BlockedVoucher.class, em);
  }

}
