package com.optiva.topup.voms.rest.resources.voucherpolicy.voucherpolicyschedule;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.interceptors.VoucherPolicyReschedule;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_POLICY_SCHEDULE + "')")
public interface VoucherPolicyScheduleResource extends JpaRepository<VoucherPolicySchedule, Integer> {

  @RestResource
  @Override
  @VoucherPolicyReschedule
  <S extends VoucherPolicySchedule> S save(S voucherPolicySchedule);

  @RestResource
  @Override
  Optional<VoucherPolicySchedule> findById(Integer integer);

  @RestResource
  @Override
  Page<VoucherPolicySchedule> findAll(Pageable pageable);

}
