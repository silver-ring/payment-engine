package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.types.UserManagementConfigParameterType;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserManagementConfigParameterRepo extends
    SimpleJpaRepository<UserManagementConfigParameter, Integer> {

  public UserManagementConfigParameterRepo(EntityManager entityManager) {
    super(UserManagementConfigParameter.class, entityManager);
  }

  public Map<UserManagementConfigParameterType, String> findAllAsMap() {
    return findAll().stream()
        .collect(
            Collectors
                .toMap(UserManagementConfigParameter::getParameter, UserManagementConfigParameter::getValue));
  }

  public UserManagementConfigParameter findByParameter(
      UserManagementConfigParameterType configParameterType) {
    UserManagementConfigParameter configParameter = new UserManagementConfigParameter();
    configParameter.setParameter(configParameterType);
    return findOne(Example.of(configParameter))
        .orElseThrow(UserManagementConfigParameterNotFoundException::new);
  }

}

