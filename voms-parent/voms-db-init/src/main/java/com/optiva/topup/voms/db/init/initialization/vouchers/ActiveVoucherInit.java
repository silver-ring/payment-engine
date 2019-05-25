package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherProviderRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.ActiveVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.CreatedVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherInit implements DataInitializer {

  @Autowired
  private ActiveVoucherRepo activeVoucherRepo;

  @Autowired
  private CreatedVoucherRepo createdVoucherRepo;

  @Autowired
  private VoucherProviderRepo voucherProviderRepo;

  public void init() {

    RandomEntityUtils<VoucherProvider, VoucherProviderRepo> voucherProviderRandom = new RandomEntityUtils<>(
        voucherProviderRepo);
    RandomEntityUtils<CreatedVoucher, CreatedVoucherRepo> createdVoucherRandom = new RandomEntityUtils<>(
        createdVoucherRepo);

    Set<CreatedVoucher> createdVouchers = createdVoucherRandom.randomEntities();
    createdVouchers.forEach(createdVoucher -> {
      ActiveVoucher activeVoucher = new ActiveVoucher();
      activeVoucher.setVoucherType(createdVoucher.getVoucherType());
      activeVoucher.setVoucherId(createdVoucher.getVoucherId());
      activeVoucher.setSerialNumber(createdVoucher.getSerialNumber());
      activeVoucher.setRechargeValue(createdVoucher.getRechargeValue());
      activeVoucher.setExpirationDate(createdVoucher.getExpirationDate());
      activeVoucher.setRechargePeriod(createdVoucher.getRechargePeriod());
      activeVoucher.setVoucherProvider(voucherProviderRandom.randomEntity());
      activeVoucherRepo.save(activeVoucher);
      createdVoucherRepo.delete(createdVoucher);
    });

  }

}
