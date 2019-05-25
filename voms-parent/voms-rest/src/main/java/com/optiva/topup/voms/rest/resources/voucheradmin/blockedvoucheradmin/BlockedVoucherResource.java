package com.optiva.topup.voms.rest.resources.voucheradmin.blockedvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BLOCKED_VOUCHER_ADMIN + "')")
public interface BlockedVoucherResource extends JpaRepository<BlockedVoucher, Integer> {

  @RestResource
  BlockedVoucher findBySerialNumber(Long serialNumber);

  @RestResource
  BlockedVoucher findByVoucherId(Long voucherId);

  @RestResource
  @Override
  <S extends BlockedVoucher> S save(S entity);

  @RestResource
  @Override
  Optional<BlockedVoucher> findById(Integer integer);

}
