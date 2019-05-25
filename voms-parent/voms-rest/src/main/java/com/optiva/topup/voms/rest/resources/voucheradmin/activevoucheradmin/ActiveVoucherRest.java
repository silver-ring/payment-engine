package com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RepositoryRestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.ACTIVE_VOUCHER_ADMIN + "')")
public class ActiveVoucherRest {

  private final AvActiveVoucherRepo activeVoucherRepo;
  private final AvBlockedVoucherRepo blockedVoucherRepo;

  @Autowired
  public ActiveVoucherRest(AvActiveVoucherRepo activeVoucherRepo, AvBlockedVoucherRepo blockedVoucherRepo) {
    this.activeVoucherRepo = activeVoucherRepo;
    this.blockedVoucherRepo = blockedVoucherRepo;
  }

  @PostMapping("/activeVouchers/{id}/block")
  public ResponseEntity<BlockedVoucher> blockVoucher(@PathVariable Integer id) {
    ActiveVoucher activeVoucher = activeVoucherRepo.findById(id).orElse(null);

    if (activeVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BlockedVoucher blockedVoucher = new BlockedVoucher();
    blockedVoucher.setSerialNumber(activeVoucher.getSerialNumber());
    blockedVoucher.setVoucherId(activeVoucher.getVoucherId());
    blockedVoucher.setExpirationDate(activeVoucher.getExpirationDate());
    blockedVoucher.setRechargePeriod(activeVoucher.getRechargePeriod());
    blockedVoucher.setRechargeValue(activeVoucher.getRechargeValue());
    blockedVoucher.setVoucherType(activeVoucher.getVoucherType());
    blockedVoucher.setVoucherProvider(activeVoucher.getVoucherProvider());
    activeVoucherRepo.delete(activeVoucher);
    BlockedVoucher newBlockedVoucher = blockedVoucherRepo.save(blockedVoucher);
    return new ResponseEntity<>(newBlockedVoucher, HttpStatus.CREATED);

  }

}
