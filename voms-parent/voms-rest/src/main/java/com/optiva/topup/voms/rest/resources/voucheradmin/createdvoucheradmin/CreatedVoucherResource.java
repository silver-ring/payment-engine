package com.optiva.topup.voms.rest.resources.voucheradmin.createdvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.CREATED_VOUCHER_ADMIN + "')")
public interface CreatedVoucherResource extends JpaRepository<CreatedVoucher, Integer> {

  @RestResource
  CreatedVoucher findBySerialNumber(Long serialNumber);

  @RestResource
  CreatedVoucher findByVoucherId(Long voucherId);

  @RestResource
  @Override
  <S extends CreatedVoucher> S save(S entity);

  @RestResource
  @Override
  Optional<CreatedVoucher> findById(Integer integer);

}
