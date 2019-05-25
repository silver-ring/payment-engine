package com.optiva.topup.voms.rest.resources.systemhistory.rechargehistory;

import com.optiva.topup.voms.common.document.RechargeHistory;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.interceptors.VoucherIdDecryption;
import com.optiva.topup.voms.rest.interceptors.VoucherIdEncryption;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.RECHARGE_HISTORY + "')")
public interface RechargeHistoryResource extends ElasticsearchRepository<RechargeHistory, String> {

  @RestResource
  @VoucherIdDecryption
  List<RechargeHistory> findAllBySerialNumberOrderByEventTimestampDesc(Long serialNumber);

  @RestResource
  @VoucherIdEncryption
  @VoucherIdDecryption
  List<RechargeHistory> findAllByVoucherIdOrderByEventTimestampDesc(Long voucherId);

  @RestResource
  @VoucherIdDecryption
  List<RechargeHistory> findAllByMsisdnOrderByEventTimestampDesc(String msisdn);

}
