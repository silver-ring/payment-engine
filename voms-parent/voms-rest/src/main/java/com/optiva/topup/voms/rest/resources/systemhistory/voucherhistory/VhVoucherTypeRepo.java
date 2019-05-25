package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VhVoucherTypeRepo extends SimpleJpaRepository<VoucherType, Integer> {

  public VhVoucherTypeRepo(EntityManager em) {
    super(VoucherType.class, em);
  }
}
