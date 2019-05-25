package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.entities.configparameters.BatchOrderConfigParameter;
import com.optiva.topup.voms.common.types.BatchOrderConfigParameterType;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BatchOrderConfigParameterRepo extends SimpleJpaRepository<BatchOrderConfigParameter, Integer> {

  public BatchOrderConfigParameterRepo(EntityManager entityManager) {
    super(BatchOrderConfigParameter.class, entityManager);
  }

  public Map<BatchOrderConfigParameterType, String> findAllAsMap() {
    return findAll().stream()
        .collect(
            Collectors.toMap(BatchOrderConfigParameter::getParameter, BatchOrderConfigParameter::getValue));
  }

  public BatchOrderConfigParameter findByParameter(
      BatchOrderConfigParameterType batchOrderConfigParameterType) {
    BatchOrderConfigParameter batchOrderConfigParameter = new BatchOrderConfigParameter();
    batchOrderConfigParameter.setParameter(batchOrderConfigParameterType);
    return findOne(Example.of(batchOrderConfigParameter))
        .orElseThrow(BatchOrderConfigParameterNotFoundException::new);
  }

}
