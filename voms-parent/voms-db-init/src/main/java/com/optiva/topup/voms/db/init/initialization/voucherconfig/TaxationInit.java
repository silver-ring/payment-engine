package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.Taxation;
import com.optiva.topup.voms.common.types.AmountType;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.TaxationRepo;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxationInit implements DataInitializer {

  @Autowired
  private TaxationRepo taxationRepo;

  public void init() {

    List<Taxation> taxations = new ArrayList<>();

    RandomTypeUtils<AmountType> amountTypeRandom = new RandomTypeUtils<>(AmountType.values());
    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 20; i++) {

      Integer start = RandomUtils.nextInt(5, 10);
      Integer end = start + RandomUtils.nextInt(5, 10);
      LocalDate startDate = LocalDate.now().plusYears(start);
      LocalDate endDate = LocalDate.now().plusYears(end);

      Taxation taxation = new Taxation();
      taxation.setName(RandomStringUtils.randomAlphabetic(30));
      taxation.setAmount(RandomUtils.nextInt(1, 50) + RandomUtils.nextDouble(0.1, 0.99));
      taxation.setStartDate(startDate);
      taxation.setEndDate(endDate);
      taxation.setAmountType(amountTypeRandom.randomEnums());
      taxation.setStatus(statusRandom.randomEnums());
      taxations.add(taxation);
    }
    taxationRepo.saveAll(taxations);
  }

}
