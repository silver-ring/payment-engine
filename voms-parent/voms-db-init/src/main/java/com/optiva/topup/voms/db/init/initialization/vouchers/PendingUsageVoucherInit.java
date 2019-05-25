package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.utils.RechargeValueCalculator;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.ActiveVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.PendingUsageVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomDateTimeUtils;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherInit implements DataInitializer {

  @Autowired
  private PendingUsageVoucherRepo pendingUsageVoucherRepo;
  @Autowired
  private ActiveVoucherRepo activeVoucherRepo;
  @Autowired
  private RechargeValueCalculator rechargeValueCalculator;

  @Transactional
  public void init() {
    RandomEntityUtils<ActiveVoucher, ActiveVoucherRepo> activeVoucherRepoRandom = new RandomEntityUtils<>(
        activeVoucherRepo);

    List<PendingUsageVoucher> pendingUsageVouchers = new ArrayList<>();

    Set<ActiveVoucher> activeVouchers = activeVoucherRepoRandom.randomEntities();

    activeVouchers.forEach((activeVoucher) -> {

      double finalValue = rechargeValueCalculator.calculateFinalValue(activeVoucher.getRechargeValue());

      PendingUsageVoucher pendingUsageVoucher = new PendingUsageVoucher();
      pendingUsageVoucher.setVoucherId(activeVoucher.getVoucherId());
      pendingUsageVoucher.setSerialNumber(activeVoucher.getSerialNumber());
      pendingUsageVoucher.setExpirationDate(activeVoucher.getExpirationDate());
      pendingUsageVoucher.setRechargePeriod(activeVoucher.getRechargePeriod());
      pendingUsageVoucher.setTransactionId(RandomStringUtils.randomAlphanumeric(10));
      pendingUsageVoucher.setMsisdn(RandomStringUtils.randomNumeric(11));
      pendingUsageVoucher.setCcid(RandomStringUtils.randomAlphanumeric(10));
      pendingUsageVoucher.setFinalRechargeValue(finalValue);
      pendingUsageVoucher.setUsageRequestTime(RandomDateTimeUtils.pastTime());
      pendingUsageVoucher.setRechargeValue(activeVoucher.getRechargeValue());
      pendingUsageVoucher.setVoucherType(activeVoucher.getVoucherType());
      pendingUsageVoucher.setVoucherProvider(activeVoucher.getVoucherProvider());
      pendingUsageVouchers.add(pendingUsageVoucher);
      activeVouchers.add(activeVoucher);
    });

    pendingUsageVoucherRepo.saveAll(pendingUsageVouchers);
    activeVoucherRepo.deleteAll(activeVouchers);

  }

}

