package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.OcsAccount;
import com.optiva.topup.voms.common.entities.voucherconfig.Promotion;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.Taxation;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.OcsAccountRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.PromotionRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.RechargeValueRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.TaxationRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RechargeValueInit implements DataInitializer {

  @Autowired
  private RechargeValueRepo rechargeValueRepo;

  @Autowired
  private OcsAccountRepo ocsAccountRepo;

  @Autowired
  private PromotionRepo promotionRepo;

  @Autowired
  private TaxationRepo taxationRepo;

  public void init() {

    List<RechargeValue> rechargeValues = new ArrayList<>();

    RandomEntityUtils<OcsAccount, OcsAccountRepo> ocsAccountRandom = new RandomEntityUtils<>(ocsAccountRepo);
    RandomEntityUtils<Promotion, PromotionRepo> promotionsRandom = new RandomEntityUtils<>(promotionRepo);
    RandomEntityUtils<Taxation, TaxationRepo> taxationsRandom = new RandomEntityUtils<>(taxationRepo);
    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 10; i++) {

      Double valueTaxation = RandomUtils.nextDouble(0.1, 0.99) + RandomUtils.nextInt(5, 10);
      Double valuePromotion = RandomUtils.nextDouble(0.1, 0.99) + RandomUtils.nextInt(5, 10);

      Double value = RandomUtils.nextDouble(0.1, 0.99) + RandomUtils.nextInt(1000, 10000);

      RechargeValue rechargeValue = new RechargeValue();
      rechargeValue.setOcsAccount(ocsAccountRandom.randomEntity());
      rechargeValue.setName(RandomStringUtils.randomAlphabetic(10));
      rechargeValue.setValue(value);
      rechargeValue.setValueTaxation(valueTaxation);
      rechargeValue.setValuePromotion(valuePromotion);
      rechargeValue.setPromotions(promotionsRandom.randomEntities());
      rechargeValue.setTaxations(taxationsRandom.randomEntities());
      rechargeValue.setStatus(statusRandom.randomEnums());
      rechargeValues.add(rechargeValue);
    }

    rechargeValueRepo.saveAll(rechargeValues);
  }

}
