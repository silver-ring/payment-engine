package com.optiva.topup.voms.rest.resources.voucherpolicy.voucherpolicyschedule;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.utils.CronExpressionValidator;
import com.optiva.topup.voms.rest.exceptions.ValidationException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VoucherPolicyScheduleValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return VoucherPolicySchedule.class.equals(clazz);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    VoucherPolicySchedule voucherPolicySchedule = (VoucherPolicySchedule) obj;
    if (!CronExpressionValidator.isValidExpression(voucherPolicySchedule.getCronExpression())) {
      throw new ValidationException("Invalid cron expression");
    }
  }

}
