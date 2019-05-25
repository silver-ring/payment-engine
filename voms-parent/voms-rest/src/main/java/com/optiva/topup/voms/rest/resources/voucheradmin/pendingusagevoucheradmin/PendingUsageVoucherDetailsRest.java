package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RepositoryRestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.PENDING_USAGE_VOUCHER_ADMIN + "')")
public class PendingUsageVoucherDetailsRest {

  private final PuvPendingUsageVoucherRepo pendingUsageVoucherRepo;
  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public PendingUsageVoucherDetailsRest(PuvPendingUsageVoucherRepo pendingUsageVoucherRepo,
      RepositoryEntityLinks repositoryEntityLinks) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
    this.repositoryEntityLinks = repositoryEntityLinks;
  }


  @GetMapping("/pendingUsageVouchers/{pendingUsageVoucherId}/pendingUsageVoucherDetails")
  public ResponseEntity<PendingUsageVoucherDetails> getPendingUsageVoucherDetails(
      @PathVariable Integer pendingUsageVoucherId) {

    PendingUsageVoucher pendingUsageVoucher = pendingUsageVoucherRepo.findById(pendingUsageVoucherId)
        .orElse(null);

    if (pendingUsageVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    PendingUsageVoucherDetails pendingUsageVoucherDetails = new PendingUsageVoucherDetails();

    pendingUsageVoucherDetails.add(linkTo(
        methodOn(PendingUsageVoucherDetailsRest.class).getPendingUsageVoucherDetails(pendingUsageVoucherId))
        .withSelfRel());
    pendingUsageVoucherDetails.add(linkTo(
        methodOn(PendingUsageVoucherDetailsRest.class).getPendingUsageVoucherDetails(pendingUsageVoucherId))
        .withRel("pendingUsageVoucherDetails"));

    VoucherType voucherType = pendingUsageVoucher.getVoucherType();
    pendingUsageVoucherDetails.setVoucherType(voucherType);
    pendingUsageVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherType.getId())
            .withRel("voucherType"));

    VoucherProvider voucherProvider = pendingUsageVoucher.getVoucherProvider();
    pendingUsageVoucherDetails.setVoucherProvider(voucherProvider);
    pendingUsageVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherProvider.class, voucherProvider.getId())
            .withRel("voucherProvider"));

    RechargeValue rechargeValue = pendingUsageVoucher.getRechargeValue();
    pendingUsageVoucherDetails.setRechargeValue(rechargeValue);
    pendingUsageVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValue.getId())
            .withRel("rechargeValue"));

    return new ResponseEntity<>(pendingUsageVoucherDetails, HttpStatus.OK);
  }

}
