package com.optiva.topup.voms.rest.resources.voucheradmin.createdvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CvCreatedVoucherRepo extends SimpleJpaRepository<CreatedVoucher, Integer> {

  public CvCreatedVoucherRepo(EntityManager em) {
    super(CreatedVoucher.class, em);
  }

}
