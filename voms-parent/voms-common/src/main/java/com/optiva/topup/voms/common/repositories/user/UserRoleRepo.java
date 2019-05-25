package com.optiva.topup.voms.common.repositories.user;

import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRepo extends SimpleJpaRepository<UserRole, Integer> {

  public UserRoleRepo(EntityManager entityManager) {
    super(UserRole.class, entityManager);
  }

}
