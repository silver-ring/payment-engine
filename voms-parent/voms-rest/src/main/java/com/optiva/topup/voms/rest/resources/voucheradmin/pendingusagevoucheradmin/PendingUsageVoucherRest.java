package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RepositoryRestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.PENDING_USAGE_VOUCHER_ADMIN + "')")
public class PendingUsageVoucherRest {

  private final PuvPendingUsageVoucherRepo pendingUsageVoucherRepo;
  private final PuvActiveVoucherRepo activeVoucherRepo;
  private final PuvBlockedVoucherRepo blockedVoucherRepo;
  private final PuvUsedVoucherRepo usedVoucherRepo;

  @Autowired
  public PendingUsageVoucherRest(PuvActiveVoucherRepo activeVoucherRepo,
      PuvBlockedVoucherRepo blockedVoucherRepo, PuvPendingUsageVoucherRepo pendingUsageVoucherRepo,
      PuvUsedVoucherRepo usedVoucherRepo) {
    this.activeVoucherRepo = activeVoucherRepo;
    this.blockedVoucherRepo = blockedVoucherRepo;
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
    this.usedVoucherRepo = usedVoucherRepo;
  }

  @PostMapping("/pendingUsageVouchers/{id}/activate")
  public ResponseEntity<ActiveVoucher> activateVoucher(@PathVariable Integer id) {
    PendingUsageVoucher pendingUsageVoucher = pendingUsageVoucherRepo.findById(id).orElse(null);

    if (pendingUsageVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ActiveVoucher activeVoucher = new ActiveVoucher();
    activeVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    activeVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    activeVoucher.setExpirationDate(pendingUsageVoucher.getExpirationDate());
    activeVoucher.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
    activeVoucher.setRechargeValue(pendingUsageVoucher.getRechargeValue());
    activeVoucher.setVoucherType(pendingUsageVoucher.getVoucherType());
    activeVoucher.setVoucherProvider(pendingUsageVoucher.getVoucherProvider());
    pendingUsageVoucherRepo.delete(pendingUsageVoucher);
    ActiveVoucher newActiveVoucher = activeVoucherRepo.save(activeVoucher);
    return new ResponseEntity<>(newActiveVoucher, HttpStatus.CREATED);
  }

  @PostMapping("/pendingUsageVouchers/{id}/block")
  public ResponseEntity<BlockedVoucher> blockVoucher(@PathVariable Integer id) {
    PendingUsageVoucher pendingUsageVoucher = pendingUsageVoucherRepo.findById(id).orElse(null);

    if (pendingUsageVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BlockedVoucher blockedVoucher = new BlockedVoucher();
    blockedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    blockedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    blockedVoucher.setExpirationDate(pendingUsageVoucher.getExpirationDate());
    blockedVoucher.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
    blockedVoucher.setRechargeValue(pendingUsageVoucher.getRechargeValue());
    blockedVoucher.setVoucherType(pendingUsageVoucher.getVoucherType());
    blockedVoucher.setVoucherProvider(pendingUsageVoucher.getVoucherProvider());
    pendingUsageVoucherRepo.delete(pendingUsageVoucher);
    BlockedVoucher newBlockedVoucher = blockedVoucherRepo.save(blockedVoucher);
    return new ResponseEntity<>(newBlockedVoucher, HttpStatus.CREATED);

  }

  @PostMapping("/pendingUsageVouchers/{id}/use")
  public ResponseEntity<UsedVoucher> useVoucher(@PathVariable Integer id) {
    PendingUsageVoucher pendingUsageVoucher = pendingUsageVoucherRepo.findById(id).orElse(null);

    if (pendingUsageVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    UsedVoucher usedVoucher = new UsedVoucher();
    usedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    usedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    usedVoucher.setFinalRechargeValue(pendingUsageVoucher.getFinalRechargeValue());
    usedVoucher.setMsisdn(pendingUsageVoucher.getMsisdn());
    usedVoucher.setCcid(pendingUsageVoucher.getCcid());
    pendingUsageVoucherRepo.delete(pendingUsageVoucher);
    UsedVoucher newUsedVoucher = usedVoucherRepo.save(usedVoucher);
    return new ResponseEntity<>(newUsedVoucher, HttpStatus.CREATED);

  }

}
