package com.optiva.topup.voms.rest.resources.configparameter.filemanagerconfigparameter;

import com.optiva.topup.voms.common.entities.configparameters.FileManagerConfigParameter;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.FILE_MANAGER_CONFIG_PARAMETER + "')")
public interface FileManagerConfigParameterResource extends
    JpaRepository<FileManagerConfigParameter, Integer> {

  @RestResource
  @Override
  <S extends FileManagerConfigParameter> S save(S entity);

  @RestResource
  @Override
  Page<FileManagerConfigParameter> findAll(Pageable pageable);

}

