package com.optiva.topup.voms.rest.resources.configparameter.usermanagementconfigparameter;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_MANAGER_CONFIG_PARAMETER + "')")
public interface UserManagementConfigParameterResource extends
    JpaRepository<UserManagementConfigParameter, Integer> {

  @RestResource
  @Override
  <S extends UserManagementConfigParameter> S save(S entity);

  @RestResource
  @Override
  Page<UserManagementConfigParameter> findAll(Pageable pageable);

}
