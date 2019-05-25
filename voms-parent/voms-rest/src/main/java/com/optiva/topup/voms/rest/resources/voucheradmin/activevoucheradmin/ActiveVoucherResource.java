package com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.ACTIVE_VOUCHER_ADMIN + "')")
public interface ActiveVoucherResource extends JpaRepository<ActiveVoucher, Integer> {

  @RestResource
  ActiveVoucher findBySerialNumber(Long serialNumber);

  @RestResource
  ActiveVoucher findByVoucherId(Long voucherId);

}
