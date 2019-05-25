package com.optiva.topup.voms.rest.resources.voucheradmin.blockedvoucheradmin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BLOCKED_VOUCHER_ADMIN + "')")
public class BlockedVoucherDetailsRest {

  private final BvBlockedVoucherRepo blockedVoucherRepo;
  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public BlockedVoucherDetailsRest(BvBlockedVoucherRepo blockedVoucherRepo,
      RepositoryEntityLinks repositoryEntityLinks) {
    this.blockedVoucherRepo = blockedVoucherRepo;
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @GetMapping("/blockedVouchers/{blockedVoucherId}/blockedVoucherDetails")
  public ResponseEntity<BlockedVoucherDetails> getBlockedVoucherDetails(
      @PathVariable Integer blockedVoucherId) {

    BlockedVoucher blockedVoucher = blockedVoucherRepo.findById(blockedVoucherId).orElse(null);

    if (blockedVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BlockedVoucherDetails blockedVoucherDetails = new BlockedVoucherDetails();

    blockedVoucherDetails.add(
        linkTo(methodOn(BlockedVoucherDetailsRest.class).getBlockedVoucherDetails(blockedVoucherId))
            .withSelfRel());
    blockedVoucherDetails.add(
        linkTo(methodOn(BlockedVoucherDetailsRest.class).getBlockedVoucherDetails(blockedVoucherId))
            .withRel("blockedVoucherDetails"));

    VoucherType voucherType = blockedVoucher.getVoucherType();
    blockedVoucherDetails.setVoucherType(voucherType);
    blockedVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherType.getId())
            .withRel("voucherType"));

    VoucherProvider voucherProvider = blockedVoucher.getVoucherProvider();
    blockedVoucherDetails.setVoucherProvider(voucherProvider);
    blockedVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherProvider.class, voucherProvider.getId())
            .withRel("voucherProvider"));

    RechargeValue rechargeValue = blockedVoucher.getRechargeValue();
    blockedVoucherDetails.setRechargeValue(rechargeValue);
    blockedVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValue.getId())
            .withRel("rechargeValue"));

    return new ResponseEntity<>(blockedVoucherDetails, HttpStatus.OK);
  }

}
