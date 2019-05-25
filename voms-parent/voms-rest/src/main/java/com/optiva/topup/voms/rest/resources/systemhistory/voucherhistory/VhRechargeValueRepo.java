package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VhRechargeValueRepo extends SimpleJpaRepository<RechargeValue, Integer> {

  public VhRechargeValueRepo(EntityManager em) {
    super(RechargeValue.class, em);
  }

}
