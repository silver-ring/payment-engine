package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class VoucherPolicyRescheduleInterceptor implements WithVoucherPolicyTopicsSupport {

  @AfterReturning(pointcut = "@annotation(voucherPolicyReschedule)",
      returning = "voucherPolicySchedule")
  public void sendBatchOrderScheduleMessage(VoucherPolicyReschedule voucherPolicyReschedule,
      VoucherPolicySchedule voucherPolicySchedule) {
    sendVoucherPolicyRescheduleTopic(voucherPolicySchedule.getId());
  }

}
