package com.optiva.topup.voms.rest.resources.voucherpolicy.voucherpolicyschedule;

import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.utils.CronExpressionValidator;
import com.optiva.topup.voms.rest.exceptions.ValidationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_POLICY_SCHEDULE + "')")
public class ValidateCronExpressionRest {

  @PostMapping("/validateCronExpression")
  public void validateCronExpression(@RequestBody ValidateCronExpressionDto validateCronExpressionDto) {
    if (!CronExpressionValidator.isValidExpression(validateCronExpressionDto.getCronExpression())) {
      throw new ValidationException("Invalid cron expression");
    }
  }

}
