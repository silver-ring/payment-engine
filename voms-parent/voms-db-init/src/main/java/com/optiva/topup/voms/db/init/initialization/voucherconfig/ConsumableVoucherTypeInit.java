package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.ConsumableVoucherType;
import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.ConsumableVoucherTypeRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.MsisdnProviderRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherProviderRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherTypeRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumableVoucherTypeInit implements DataInitializer {

  @Autowired
  private ConsumableVoucherTypeRepo consumableVoucherTypeRepo;
  @Autowired
  private VoucherProviderRepo voucherProviderRepo;
  @Autowired
  private VoucherTypeRepo voucherTypeRepo;
  @Autowired
  private MsisdnProviderRepo msisdnProviderRepo;

  public void init() {
    List<ConsumableVoucherType> consumableVoucherTypes = new ArrayList<>();

    RandomEntityUtils<VoucherProvider, VoucherProviderRepo> voucherProviderRandom = new RandomEntityUtils<>(
        voucherProviderRepo);
    RandomEntityUtils<VoucherType, VoucherTypeRepo> voucherTypeRandom = new RandomEntityUtils<>(
        voucherTypeRepo);
    RandomEntityUtils<MsisdnProvider, MsisdnProviderRepo> msisdnProviderRandom = new RandomEntityUtils<>(
        msisdnProviderRepo);

    for (int i = 0; i < 10; i++) {

      String name = RandomStringUtils.randomAlphabetic(10);
      Status status = RandomUtils.nextBoolean() ? Status.ACTIVE : Status.INACTIVE;

      ConsumableVoucherType consumableVoucherType = new ConsumableVoucherType();
      consumableVoucherType.setName(name);
      consumableVoucherType.setMsisdnProvider(msisdnProviderRandom.randomEntity());
      consumableVoucherType.setVoucherProvider(voucherProviderRandom.randomEntity());
      consumableVoucherType.setVoucherType(voucherTypeRandom.randomEntity());
      consumableVoucherType.setStatus(status);
      consumableVoucherTypes.add(consumableVoucherType);
    }
    consumableVoucherTypeRepo.saveAll(consumableVoucherTypes);
  }

}
