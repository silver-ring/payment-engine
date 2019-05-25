package com.optiva.topup.voms.common.repositories.user;

import com.optiva.topup.voms.common.entities.usermanager.GuiUserAuthority;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GuiUserAuthorityRepo extends SimpleJpaRepository<GuiUserAuthority, Integer> {

  public GuiUserAuthorityRepo(EntityManager entityManager) {
    super(GuiUserAuthority.class, entityManager);
  }

}
