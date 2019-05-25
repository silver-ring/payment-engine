package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.entities.configparameters.VoucherPolicyConfigParameter;
import com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherPolicyConfigParameterRepo extends
    SimpleJpaRepository<VoucherPolicyConfigParameter, Integer> {

  public VoucherPolicyConfigParameterRepo(EntityManager entityManager) {
    super(VoucherPolicyConfigParameter.class, entityManager);
  }

  Map<VoucherPolicyConfigParameterType, String> findAllAsMap() {
    return findAll().stream()
        .collect(
            Collectors
                .toMap(VoucherPolicyConfigParameter::getParameter, VoucherPolicyConfigParameter::getValue));
  }

  public VoucherPolicyConfigParameter findByParameter(
      VoucherPolicyConfigParameterType configParameterType) {
    VoucherPolicyConfigParameter configParameter = new VoucherPolicyConfigParameter();
    configParameter.setParameter(configParameterType);
    return findOne(Example.of(configParameter))
        .orElseThrow(VoucherPolicyConfigParameterNotFoundException::new);
  }

}
