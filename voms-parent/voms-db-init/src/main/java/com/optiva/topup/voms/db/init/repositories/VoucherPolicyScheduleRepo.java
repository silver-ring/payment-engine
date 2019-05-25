package com.optiva.topup.voms.db.init.repositories;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherPolicyScheduleRepo extends JpaRepository<VoucherPolicySchedule, Integer> {

}
