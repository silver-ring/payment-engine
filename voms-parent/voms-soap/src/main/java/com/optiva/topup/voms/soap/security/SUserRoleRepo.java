package com.optiva.topup.voms.soap.security;

import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SUserRoleRepo extends JpaRepository<UserRole, Integer> {

  @Override
  @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"apiUserAuthorities"})
  List<UserRole> findAllById(Iterable<Integer> integers);
}
