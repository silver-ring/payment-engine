package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.MsisdnProviderRepo;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsisdnProviderInit implements DataInitializer {

  @Autowired
  private MsisdnProviderRepo msisdnProviderRepo;

  public void init() {
    List<MsisdnProvider> msisdnProviders = new ArrayList<>();

    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 10; i++) {

      int idAtIn = RandomUtils.nextInt();
      String name = RandomStringUtils.randomAlphabetic(10);

      MsisdnProvider msisdnProvider = new MsisdnProvider();
      msisdnProvider.setIdAtIn(idAtIn);
      msisdnProvider.setName(name);
      msisdnProvider.setStatus(statusRandom.randomEnums());
      msisdnProviders.add(msisdnProvider);
    }
    msisdnProviderRepo.saveAll(msisdnProviders);
  }

}
