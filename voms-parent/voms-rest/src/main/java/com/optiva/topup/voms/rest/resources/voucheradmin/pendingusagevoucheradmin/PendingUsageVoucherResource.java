package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.PENDING_USAGE_VOUCHER_ADMIN + "')")
public interface PendingUsageVoucherResource extends JpaRepository<PendingUsageVoucher, Integer> {

  @RestResource
  PendingUsageVoucher findBySerialNumber(Long serialNumber);

  @RestResource
  PendingUsageVoucher findByVoucherId(Long voucherId);

}
