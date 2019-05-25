package com.optiva.topup.voms.rest.resources.configparameter.batchorderconfigparameter;

import com.optiva.topup.voms.common.entities.configparameters.BatchOrderConfigParameter;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BATCH_ORDER_CONFIG_PARAMETER + "')")
public interface BatchOrderConfigParameterResource extends JpaRepository<BatchOrderConfigParameter, Integer> {

  @RestResource
  @Override
  <S extends BatchOrderConfigParameter> S save(S entity);

  @RestResource
  @Override
  Page<BatchOrderConfigParameter> findAll(Pageable pageable);

}
