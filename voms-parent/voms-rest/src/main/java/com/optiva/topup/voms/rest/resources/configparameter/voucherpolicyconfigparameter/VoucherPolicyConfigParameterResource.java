package com.optiva.topup.voms.rest.resources.configparameter.voucherpolicyconfigparameter;

import com.optiva.topup.voms.common.entities.configparameters.VoucherPolicyConfigParameter;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_POLICY_CONFIG_PARAMETER + "')")
public interface VoucherPolicyConfigParameterResource extends
    JpaRepository<VoucherPolicyConfigParameter, Integer> {

  @RestResource
  @Override
  <S extends VoucherPolicyConfigParameter> S save(S entity);

  @RestResource
  @Override
  Page<VoucherPolicyConfigParameter> findAll(Pageable pageable);

}
