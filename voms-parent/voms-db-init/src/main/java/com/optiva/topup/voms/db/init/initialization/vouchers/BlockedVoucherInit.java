package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.ActiveVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.BlockedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.PendingUsageVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherInit implements DataInitializer {

  @Autowired
  private ActiveVoucherRepo activeVoucherRepo;
  @Autowired
  private PendingUsageVoucherRepo pendingUsageVoucherRepo;
  @Autowired
  private BlockedVoucherRepo blockedVoucherRepo;

  public void init() {

    RandomEntityUtils<ActiveVoucher, ActiveVoucherRepo> activeVoucherRandom = new RandomEntityUtils<>(
        activeVoucherRepo);
    RandomEntityUtils<PendingUsageVoucher, PendingUsageVoucherRepo> pendingUsageVoucherRandom =
        new RandomEntityUtils<>(
            pendingUsageVoucherRepo);

    Set<ActiveVoucher> activeVouchers = activeVoucherRandom.randomEntities();
    Set<PendingUsageVoucher> pendingUsageVouchers = pendingUsageVoucherRandom.randomEntities();

    activeVouchers.forEach(activeVoucher -> {
      BlockedVoucher blockedVoucher = new BlockedVoucher();
      blockedVoucher.setVoucherProvider(activeVoucher.getVoucherProvider());
      blockedVoucher.setVoucherType(activeVoucher.getVoucherType());
      blockedVoucher.setRechargeValue(activeVoucher.getRechargeValue());
      blockedVoucher.setRechargePeriod(activeVoucher.getRechargePeriod());
      blockedVoucher.setExpirationDate(activeVoucher.getExpirationDate());
      blockedVoucher.setVoucherId(activeVoucher.getVoucherId());
      blockedVoucher.setSerialNumber(activeVoucher.getSerialNumber());
      activeVoucherRepo.delete(activeVoucher);
      blockedVoucherRepo.save(blockedVoucher);
    });

    pendingUsageVouchers.forEach(pendingUsageVoucher -> {
      BlockedVoucher blockedVoucher = new BlockedVoucher();
      blockedVoucher.setVoucherProvider(pendingUsageVoucher.getVoucherProvider());
      blockedVoucher.setVoucherType(pendingUsageVoucher.getVoucherType());
      blockedVoucher.setRechargeValue(pendingUsageVoucher.getRechargeValue());
      blockedVoucher.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
      blockedVoucher.setExpirationDate(pendingUsageVoucher.getExpirationDate());
      blockedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
      blockedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
      pendingUsageVoucherRepo.delete(pendingUsageVoucher);
      blockedVoucherRepo.save(blockedVoucher);
    });

  }
}
