package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherPolicyScheduleRepo extends JpaRepository<VoucherPolicySchedule, Integer> {

}
