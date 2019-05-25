package com.optiva.topup.voms.rest.resources.systemhistory.remotefiletransferhistory;

import com.optiva.topup.voms.common.document.RemoteFileTransferHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.REMOTE_FILE_TRANSFER_HISTORY + "')")
public interface RemoteFileTransferHistoryResource extends
    ElasticsearchRepository<RemoteFileTransferHistory, String> {

  @RestResource
  @Override
  Page<RemoteFileTransferHistory> findAll(Pageable pageable);

}
