package com.optiva.topup.voms.rest.resources.usermanager.useractivity;

import com.optiva.topup.voms.common.document.UserActivity;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_ACTIVITY + "')")
public interface UserActivityResource extends ElasticsearchRepository<UserActivity, String> {

  @RestResource
  List<UserActivity> findAllByEmailOrderByEventTimestampDesc(String username);

}
