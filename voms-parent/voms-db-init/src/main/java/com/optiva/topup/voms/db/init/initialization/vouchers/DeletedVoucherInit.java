package com.optiva.topup.voms.db.init.initialization.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.vouchers.BlockedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.DeletedVoucherRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.UsedVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeletedVoucherInit implements DataInitializer {

  @Autowired
  private DeletedVoucherRepo deletedVoucherRepo;
  @Autowired
  private UsedVoucherRepo usedVoucherRepo;
  @Autowired
  private BlockedVoucherRepo blockedVoucherRepo;

  public void init() {
    RandomEntityUtils<UsedVoucher, UsedVoucherRepo> usedVoucherRepoRandom = new RandomEntityUtils<>(
        usedVoucherRepo);
    RandomEntityUtils<BlockedVoucher, BlockedVoucherRepo> blockedVoucherRepoRandom = new RandomEntityUtils<>(
        blockedVoucherRepo);

    Set<UsedVoucher> usedVouchersRandom = usedVoucherRepoRandom.randomEntities();
    Set<BlockedVoucher> blockedVoucherRandom = blockedVoucherRepoRandom.randomEntities();

    usedVouchersRandom.forEach(usedVoucher -> {
      DeletedVoucher deletedVoucher = new DeletedVoucher();
      deletedVoucher.setVoucherId(usedVoucher.getVoucherId());
      deletedVoucher.setSerialNumber(usedVoucher.getSerialNumber());
      deletedVoucherRepo.save(deletedVoucher);
      usedVoucherRepo.delete(usedVoucher);
    });

    blockedVoucherRandom.forEach(blockedVoucher -> {
      DeletedVoucher deletedVoucher = new DeletedVoucher();
      deletedVoucher.setVoucherId(blockedVoucher.getVoucherId());
      deletedVoucher.setSerialNumber(blockedVoucher.getSerialNumber());
      deletedVoucherRepo.save(deletedVoucher);
      blockedVoucherRepo.delete(blockedVoucher);
    });

  }

}
