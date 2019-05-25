package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.CreatedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.DeletedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.DestroyedVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DestroyedVoucherInit implements DataInitializer {

  @Autowired
  private DeletedVoucherRepo deletedVoucherRepo;
  @Autowired
  private DestroyedVoucherRepo destroyedVoucherRepo;
  @Autowired
  private CreatedVoucherRepo createdVoucherRepo;

  public void init() {
    RandomEntityUtils<DeletedVoucher, DeletedVoucherRepo> deletedVoucherRandom = new RandomEntityUtils<>(
        deletedVoucherRepo);

    RandomEntityUtils<CreatedVoucher, CreatedVoucherRepo> createdVoucherRandom = new RandomEntityUtils<>(
        createdVoucherRepo);

    List<DestroyedVoucher> destroyedVouchers = new ArrayList<>();

    Set<DeletedVoucher> deletedVouchers = deletedVoucherRandom.randomEntities();
    deletedVouchers.forEach(deletedVoucher -> {
      DestroyedVoucher destroyedVoucher = new DestroyedVoucher();
      destroyedVoucher.setVoucherId(deletedVoucher.getVoucherId());
      destroyedVoucher.setSerialNumber(deletedVoucher.getSerialNumber());
      deletedVoucherRepo.delete(deletedVoucher);
      destroyedVoucherRepo.save(destroyedVoucher);
      destroyedVouchers.add(destroyedVoucher);
    });

    Set<CreatedVoucher> createdVouchers = createdVoucherRandom.randomEntities();
    createdVouchers.forEach(createdVoucher -> {
      DestroyedVoucher destroyedVoucher = new DestroyedVoucher();
      destroyedVoucher.setVoucherId(createdVoucher.getVoucherId());
      destroyedVoucher.setSerialNumber(createdVoucher.getSerialNumber());
      createdVoucherRepo.delete(createdVoucher);
      destroyedVoucherRepo.save(destroyedVoucher);
      destroyedVouchers.add(destroyedVoucher);
    });

  }

}
