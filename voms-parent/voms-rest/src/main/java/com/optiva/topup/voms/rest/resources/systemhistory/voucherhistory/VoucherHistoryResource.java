package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.interceptors.VoucherIdDecryption;
import com.optiva.topup.voms.rest.interceptors.VoucherIdEncryption;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_HISTORY + "')")
public interface VoucherHistoryResource extends ElasticsearchRepository<VoucherHistory, String> {

  @RestResource
  @VoucherIdDecryption
  List<VoucherHistory> findAllBySerialNumberOrderByEventTimestampDesc(Long serialNumber);

  @RestResource
  @VoucherIdEncryption
  @VoucherIdDecryption
  List<VoucherHistory> findAllByVoucherIdOrderByEventTimestampDesc(Long voucherId);

}
