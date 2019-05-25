package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BohRechargeValueRepo extends SimpleJpaRepository<RechargeValue, Integer> {

  public BohRechargeValueRepo(EntityManager em) {
    super(RechargeValue.class, em);
  }

}
