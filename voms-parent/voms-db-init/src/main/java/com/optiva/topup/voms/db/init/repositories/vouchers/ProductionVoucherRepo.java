package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionVoucherRepo extends JpaRepository<ProductionVoucher, Integer> {

  void deleteBySerialNumber(Long serialNumber);

}
