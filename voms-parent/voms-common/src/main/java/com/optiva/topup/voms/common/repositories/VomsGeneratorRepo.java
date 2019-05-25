package com.optiva.topup.voms.common.repositories;

import com.optiva.topup.voms.common.entities.voucherconfig.VomsGenerator;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VomsGeneratorRepo extends SimpleJpaRepository<VomsGenerator, Integer> {

  public VomsGeneratorRepo(EntityManager entityManager) {
    super(VomsGenerator.class, entityManager);
  }

  public VomsGenerator voucherSerialNumber() {
    return findById(1).get();
  }

}
