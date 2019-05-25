package com.optiva.topup.voms.db.init.initialization.configparameters;

import static com.optiva.topup.voms.common.types.BatchOrderConfigParameterType.VOUCHER_ID_LENGTH;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.NUMBER;

import com.optiva.topup.voms.common.entities.configparameters.BatchOrderConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.BatchOrderConfigParameterRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchOrderConfigParameterInit implements DataInitializer {

  @Autowired
  private BatchOrderConfigParameterRepo batchOrderConfigParameterRepo;

  public void init() {

    final List<BatchOrderConfigParameter> batchOrderConfigParameters = new ArrayList<>();

    batchOrderConfigParameters.add(getVoucherIdLengthParameter());

    batchOrderConfigParameterRepo.saveAll(batchOrderConfigParameters);
  }

  private BatchOrderConfigParameter getVoucherIdLengthParameter() {
    BatchOrderConfigParameter configParameter = new BatchOrderConfigParameter();
    configParameter.setParameter(VOUCHER_ID_LENGTH);
    configParameter.setDescription("Length of generated Voucher Id");
    configParameter.setDefaultValue("14");
    configParameter.setValue("14");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

}
