package com.optiva.topup.voms.rest.resources.systemhistory.useremaildomainmigrationhistory;

import com.optiva.topup.voms.common.document.UserEmailDomainMigrationHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_EMAIL_DOMAIN_MIGRATION_HISTORY + "')")
public interface UserEmailDomainMigrationHistoryResource extends
    ElasticsearchRepository<UserEmailDomainMigrationHistory, String> {

  @RestResource
  @Override
  Page<UserEmailDomainMigrationHistory> findAll(Pageable pageable);
}
