package com.optiva.topup.voms.rest.resources.voucheradmin.createdvoucheradmin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.CREATED_VOUCHER_ADMIN + "')")
public class CreatedVoucherDetailsRest {

  private final CvCreatedVoucherRepo createdVoucherRepo;
  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public CreatedVoucherDetailsRest(CvCreatedVoucherRepo createdVoucherRepo,
      RepositoryEntityLinks repositoryEntityLinks) {
    this.createdVoucherRepo = createdVoucherRepo;
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @GetMapping("/createdVouchers/{createdVoucherId}/createdVoucherDetails")
  public ResponseEntity<CreatedVoucherDetails> getCreatedVoucherDetails(
      @PathVariable Integer createdVoucherId) {

    CreatedVoucher createdVoucher = createdVoucherRepo.findById(createdVoucherId).orElse(null);

    if (createdVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    CreatedVoucherDetails createdVoucherDetails = new CreatedVoucherDetails();

    createdVoucherDetails.add(
        linkTo(methodOn(CreatedVoucherDetailsRest.class).getCreatedVoucherDetails(createdVoucherId))
            .withSelfRel());
    createdVoucherDetails.add(
        linkTo(methodOn(CreatedVoucherDetailsRest.class).getCreatedVoucherDetails(createdVoucherId))
            .withRel("blockedVoucherDetails"));

    VoucherType voucherType = createdVoucher.getVoucherType();
    createdVoucherDetails.setVoucherType(voucherType);
    createdVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherType.getId())
            .withRel("voucherType"));

    RechargeValue rechargeValue = createdVoucher.getRechargeValue();
    createdVoucherDetails.setRechargeValue(rechargeValue);
    createdVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValue.getId())
            .withRel("rechargeValue"));

    return new ResponseEntity<>(createdVoucherDetails, HttpStatus.OK);
  }

}
