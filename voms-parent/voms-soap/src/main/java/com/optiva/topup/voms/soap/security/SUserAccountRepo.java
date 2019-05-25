package com.optiva.topup.voms.soap.security;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SUserAccountRepo extends JpaRepository<UserAccount, Integer> {

  @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"userRoles"})
  UserAccount findByEmailAndUserAccountStatus(String email, UserAccountStatus userAccountStatus);

}
