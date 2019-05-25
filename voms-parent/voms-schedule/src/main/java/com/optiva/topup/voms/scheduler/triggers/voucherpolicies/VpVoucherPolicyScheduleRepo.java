package com.optiva.topup.voms.scheduler.triggers.voucherpolicies;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VpVoucherPolicyScheduleRepo extends JpaRepository<VoucherPolicySchedule, Integer> {

}
