package com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.ACTIVE_VOUCHER_ADMIN + "')")
public class ActiveVoucherDetailsRest {

  private final AvActiveVoucherRepo activeVoucherRepo;
  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public ActiveVoucherDetailsRest(AvActiveVoucherRepo activeVoucherRepo,
      RepositoryEntityLinks repositoryEntityLinks) {
    this.activeVoucherRepo = activeVoucherRepo;
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @GetMapping("/activeVouchers/{activeVoucherId}/activeVoucherDetails")
  public ResponseEntity<ActiveVoucherDetails> getActiveVoucherDetails(@PathVariable Integer activeVoucherId) {

    ActiveVoucher activeVoucher = activeVoucherRepo.findById(activeVoucherId).orElse(null);

    if (activeVoucher == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ActiveVoucherDetails activeVoucherDetails = new ActiveVoucherDetails();

    activeVoucherDetails.add(
        linkTo(methodOn(ActiveVoucherDetailsRest.class).getActiveVoucherDetails(activeVoucherId))
            .withSelfRel());
    activeVoucherDetails.add(
        linkTo(methodOn(ActiveVoucherDetailsRest.class).getActiveVoucherDetails(activeVoucherId))
            .withRel("activeVoucherDetails"));

    VoucherType voucherType = activeVoucher.getVoucherType();
    activeVoucherDetails.setVoucherType(voucherType);
    activeVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherType.getId())
            .withRel("voucherType"));

    VoucherProvider voucherProvider = activeVoucher.getVoucherProvider();
    activeVoucherDetails.setVoucherProvider(voucherProvider);
    activeVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(VoucherProvider.class, voucherProvider.getId())
            .withRel("voucherProvider"));

    RechargeValue rechargeValue = activeVoucher.getRechargeValue();
    activeVoucherDetails.setRechargeValue(rechargeValue);
    activeVoucherDetails.add(
        repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValue.getId())
            .withRel("rechargeValue"));

    return new ResponseEntity<>(activeVoucherDetails, HttpStatus.OK);
  }

}
