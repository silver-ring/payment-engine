package com.optiva.topup.voms.rest.resources.configparameter.emailconfigparameter;

import com.optiva.topup.voms.common.entities.configparameters.EmailConfigParameter;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.EMAIL_CONFIG_PARAMETER + "')")
public interface EmailConfigParameterResource extends
    JpaRepository<EmailConfigParameter, Integer> {

  @RestResource
  @Override
  <S extends EmailConfigParameter> S save(S entity);

  @RestResource
  @Override
  Page<EmailConfigParameter> findAll(Pageable pageable);

}
