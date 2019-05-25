package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BohVoucherTypeRepo extends SimpleJpaRepository<VoucherType, Integer> {

  public BohVoucherTypeRepo(EntityManager em) {
    super(VoucherType.class, em);
  }
}
