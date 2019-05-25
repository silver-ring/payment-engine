package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.CreatedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.ProductionVoucherRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductionVoucherInit implements DataInitializer {

  @Autowired
  private ProductionVoucherRepo productionVoucherRepo;
  @Autowired
  private CreatedVoucherRepo createdVoucherRepo;

  public void init() {

    List<ProductionVoucher> productionVouchers = new ArrayList<>();
    List<CreatedVoucher> createdVouchers = createdVoucherRepo.findAll();

    createdVouchers.forEach((createdVoucher) -> {
      ProductionVoucher productionVoucher = new ProductionVoucher();
      productionVoucher.setVoucherId(createdVoucher.getVoucherId());
      productionVoucher.setSerialNumber(createdVoucher.getSerialNumber());
      productionVoucher.setExpirationDate(createdVoucher.getExpirationDate());
      productionVoucher.setRechargePeriod(createdVoucher.getRechargePeriod());
      productionVouchers.add(productionVoucher);
    });

    productionVoucherRepo.saveAll(productionVouchers);
  }

}
