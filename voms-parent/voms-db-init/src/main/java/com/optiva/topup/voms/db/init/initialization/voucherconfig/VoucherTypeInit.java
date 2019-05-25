package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherTypeRepo;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherTypeInit implements DataInitializer {

  @Autowired
  private VoucherTypeRepo voucherTypeRepo;

  public void init() {

    List<VoucherType> voucherTypes = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      VoucherType voucherType = new VoucherType();
      voucherType.setIdAtIn(RandomUtils.nextInt());
      voucherType.setName(RandomStringUtils.randomAlphabetic(10));
      voucherType.setStatus(RandomUtils.nextBoolean() ? Status.ACTIVE : Status.INACTIVE);
      voucherTypes.add(voucherType);
    }

    voucherTypeRepo.saveAll(voucherTypes);
  }

}
