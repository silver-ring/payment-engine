package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.Currency;
import com.optiva.topup.voms.common.entities.voucherconfig.OcsAccount;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.OcsAccountRepo;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OcsAccountInit implements DataInitializer {

  @Autowired
  private OcsAccountRepo ocsAccountRepo;

  public void init() {
    List<OcsAccount> ocsAccounts = new ArrayList<>();

    for (int i = 0; i < 5; i++) {

      Integer base = RandomUtils.nextInt(0, 4);
      String unit = RandomStringUtils.randomAlphabetic(3);
      Currency currency = new Currency();
      currency.setBase(base);
      currency.setUnit(unit);

      Integer idAtOcs = RandomUtils.nextInt();
      String ocsAccountName = RandomStringUtils.randomAlphabetic(10);
      Status ocsAccountStatus = RandomUtils.nextBoolean() ? Status.ACTIVE : Status.INACTIVE;

      OcsAccount ocsAccount = new OcsAccount();
      ocsAccount.setCurrency(currency);
      ocsAccount.setIdAtOcs(idAtOcs);
      ocsAccount.setName(ocsAccountName);
      ocsAccount.setStatus(ocsAccountStatus);

      ocsAccounts.add(ocsAccount);
    }
    ocsAccountRepo.saveAll(ocsAccounts);
  }

}
