package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.entities.configparameters.EmailConfigParameter;
import com.optiva.topup.voms.common.types.EmailConfigParameterType;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EmailConfigParameterRepo extends SimpleJpaRepository<EmailConfigParameter, Integer> {

  public EmailConfigParameterRepo(EntityManager entityManager) {
    super(EmailConfigParameter.class, entityManager);
  }

  public Map<EmailConfigParameterType, String> findAllAsMap() {
    return findAll().stream()
        .collect(
            Collectors.toMap(EmailConfigParameter::getParameter, EmailConfigParameter::getValue));
  }

  public EmailConfigParameter findByParameter(
      EmailConfigParameterType emailConfigParameterType) {
    EmailConfigParameter emailConfigParameter = new EmailConfigParameter();
    emailConfigParameter.setParameter(emailConfigParameterType);
    return findOne(Example.of(emailConfigParameter)).orElseThrow(EmailConfigParameterNotFoundException::new);
  }

}
