package com.optiva.topup.voms.db.init.initialization.configparameters;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.NUMBER;
import static com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType.BLOCKED_VOUCHER_KEEP_DAYS;
import static com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType.PRODUCTION_VOUCHER_KEEP_DAYS;

import com.optiva.topup.voms.common.entities.configparameters.VoucherPolicyConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.VoucherPolicyConfigParameterRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherPolicyConfigParameterInit implements DataInitializer {

  @Autowired
  private VoucherPolicyConfigParameterRepo configParameterRepo;

  public void init() {

    final List<VoucherPolicyConfigParameter> voucherConfigParameters = new ArrayList<>();

    voucherConfigParameters.add(getBlockedVoucherKeepDaysParameter());
    voucherConfigParameters.add(getProductionVoucherKeepDaysParameter());

    configParameterRepo.saveAll(voucherConfigParameters);
  }

  private VoucherPolicyConfigParameter getProductionVoucherKeepDaysParameter() {
    VoucherPolicyConfigParameter configParameter = new VoucherPolicyConfigParameter();
    configParameter.setParameter(PRODUCTION_VOUCHER_KEEP_DAYS);
    configParameter
        .setDescription("Number of days to keep Production Voucher before it's Destroyed");
    configParameter.setDefaultValue("512");
    configParameter.setValue("512");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private VoucherPolicyConfigParameter getBlockedVoucherKeepDaysParameter() {
    VoucherPolicyConfigParameter configParameter = new VoucherPolicyConfigParameter();
    configParameter.setParameter(BLOCKED_VOUCHER_KEEP_DAYS);
    configParameter
        .setDescription("Number of days to keep Blocked Voucher before it's Deleted");
    configParameter.setDefaultValue("30");
    configParameter.setValue("30");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

}
