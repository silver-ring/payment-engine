package com.optiva.topup.voms.rest.resources.systemhistory.usernotificationemailhistory;

import com.optiva.topup.voms.common.document.UserNotificationEmailHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_NOTIFICATION_EMAIL_HISTORY + "')")
public interface UserNotificationEmailHistoryResource extends
    ElasticsearchRepository<UserNotificationEmailHistory, String> {

  @RestResource
  @Override
  Page<UserNotificationEmailHistory> findAll(Pageable pageable);

}
