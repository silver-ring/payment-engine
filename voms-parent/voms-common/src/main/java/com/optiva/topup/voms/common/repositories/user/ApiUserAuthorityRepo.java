package com.optiva.topup.voms.common.repositories.user;

import com.optiva.topup.voms.common.entities.usermanager.ApiUserAuthority;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ApiUserAuthorityRepo extends SimpleJpaRepository<ApiUserAuthority, Integer> {

  public ApiUserAuthorityRepo(EntityManager entityManager) {
    super(ApiUserAuthority.class, entityManager);
  }

}
