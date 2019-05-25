package com.optiva.topup.voms.rest.resources.voucheradmin.blockedvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin.AvActiveVoucherRepo;
import com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin.AvBlockedVoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RepositoryRestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BLOCKED_VOUCHER_ADMIN + "')")
public class BlockedVoucherRest {

  private final AvActiveVoucherRepo activeVoucherRepo;
  private final AvBlockedVoucherRepo blockedVoucherRepo;

  @Autowired
  public BlockedVoucherRest(AvActiveVoucherRepo activeVoucherRepo, AvBlockedVoucherRepo blockedVoucherRepo) {
    this.activeVoucherRepo = activeVoucherRepo;
    this.blockedVoucherRepo = blockedVoucherRepo;
  }

  @PostMapping("/blockedVouchers/{id}/activate")
  public ResponseEntity<ActiveVoucher> activateVoucher(@PathVariable Integer id) {
    BlockedVoucher blockedVoucher = blockedVoucherRepo.findById(id).orElse(null);

    if (blockedVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ActiveVoucher activeVoucher = new ActiveVoucher();
    activeVoucher.setSerialNumber(blockedVoucher.getSerialNumber());
    activeVoucher.setVoucherId(blockedVoucher.getVoucherId());
    activeVoucher.setExpirationDate(blockedVoucher.getExpirationDate());
    activeVoucher.setRechargePeriod(blockedVoucher.getRechargePeriod());
    activeVoucher.setRechargeValue(blockedVoucher.getRechargeValue());
    activeVoucher.setVoucherType(blockedVoucher.getVoucherType());
    activeVoucher.setVoucherProvider(blockedVoucher.getVoucherProvider());
    blockedVoucherRepo.delete(blockedVoucher);
    ActiveVoucher newActiveVoucher = activeVoucherRepo.save(activeVoucher);
    return new ResponseEntity<>(newActiveVoucher, HttpStatus.CREATED);

  }

}
