package com.optiva.topup.voms.db.init.repositories.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherPrinterRepo extends JpaRepository<VoucherPrinter, Integer> {

}
