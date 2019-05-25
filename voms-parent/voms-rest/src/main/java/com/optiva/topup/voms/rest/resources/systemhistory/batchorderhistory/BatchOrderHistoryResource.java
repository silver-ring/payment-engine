package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BATCH_ORDER_HISTORY + "')")
public interface BatchOrderHistoryResource extends ElasticsearchRepository<BatchOrderHistory, String> {

  @RestResource
  List<BatchOrderHistory> findAllByTagIdOrderByEventTimestampDesc(String tagId);

}
