package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.Promotion;
import com.optiva.topup.voms.common.types.AmountType;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.PromotionRepo;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionInit implements DataInitializer {

  @Autowired
  private PromotionRepo promotionRepo;

  public void init() {

    List<Promotion> promotions = new ArrayList<>();

    RandomTypeUtils<AmountType> amountTypeRandom = new RandomTypeUtils<>(AmountType.values());
    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 20; i++) {

      Integer start = RandomUtils.nextInt(0, 5);
      Integer end = RandomUtils.nextInt(6, 10);
      LocalDate startDate = LocalDate.now().plusYears(start);
      LocalDate endDate = LocalDate.now().plusYears(end);

      Promotion promotion = new Promotion();
      promotion.setName(RandomStringUtils.randomAlphabetic(30));
      promotion.setAmount(RandomUtils.nextInt(50, 500) + RandomUtils.nextDouble(0.1, 0.99));
      promotion.setStartDate(startDate);
      promotion.setEndDate(endDate);
      promotion.setAmountType(amountTypeRandom.randomEnums());
      promotion.setStatus(statusRandom.randomEnums());
      promotions.add(promotion);
    }
    promotionRepo.saveAll(promotions);
  }

}
