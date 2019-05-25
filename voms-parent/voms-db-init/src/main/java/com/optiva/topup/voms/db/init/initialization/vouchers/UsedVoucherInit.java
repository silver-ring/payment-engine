package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.PendingUsageVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.UsedVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsedVoucherInit implements DataInitializer {

  @Autowired
  private UsedVoucherRepo usedVoucherRepo;
  @Autowired
  private PendingUsageVoucherRepo pendingUsageVoucherRepo;

  public void init() {

    RandomEntityUtils<PendingUsageVoucher, PendingUsageVoucherRepo> pendingUsageVoucherRepoRandom =
        new RandomEntityUtils<>(
            pendingUsageVoucherRepo);

    Set<PendingUsageVoucher> pendingUsageVouchers = pendingUsageVoucherRepoRandom.randomEntities();

    pendingUsageVouchers.forEach(pendingUsageVoucher -> {
      UsedVoucher usedVoucher = new UsedVoucher();
      usedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
      usedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
      usedVoucher.setFinalRechargeValue(pendingUsageVoucher.getFinalRechargeValue());
      usedVoucher.setMsisdn(pendingUsageVoucher.getMsisdn());
      usedVoucher.setCcid(pendingUsageVoucher.getCcid());
      usedVoucherRepo.save(usedVoucher);
      pendingUsageVoucherRepo.delete(pendingUsageVoucher);
    });

  }
}
