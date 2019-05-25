package com.optiva.topup.voms.rest.resources.systemhistory.voucherpolicyhistory;

import com.optiva.topup.voms.common.document.VoucherPolicyHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_POLICY_HISTORY + "')")
public interface VoucherPolicyHistoryResource extends ElasticsearchRepository<VoucherPolicyHistory, String> {

  @RestResource
  @Override
  Page<VoucherPolicyHistory> findAll(Pageable pageable);

}
