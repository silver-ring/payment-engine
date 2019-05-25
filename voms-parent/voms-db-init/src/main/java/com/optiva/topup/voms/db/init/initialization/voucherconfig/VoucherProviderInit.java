package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherProviderRepo;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherProviderInit implements DataInitializer {

  @Autowired
  private VoucherProviderRepo voucherProviderRepo;

  public void init() {

    List<VoucherProvider> voucherProviders = new ArrayList<>();
    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 10; i++) {
      VoucherProvider voucherProvider = new VoucherProvider();
      voucherProvider.setId(RandomUtils.nextInt());
      voucherProvider.setName(RandomStringUtils.randomAlphabetic(20));
      voucherProvider.setIdAtIn(RandomUtils.nextInt());
      voucherProvider.setStatus(statusRandom.randomEnums());
      voucherProviders.add(voucherProvider);
    }
    voucherProviderRepo.saveAll(voucherProviders);
  }

}
