package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BohVoucherPrinterRepo extends SimpleJpaRepository<VoucherPrinter, Integer> {

  public BohVoucherPrinterRepo(EntityManager em) {
    super(VoucherPrinter.class, em);
  }

}
