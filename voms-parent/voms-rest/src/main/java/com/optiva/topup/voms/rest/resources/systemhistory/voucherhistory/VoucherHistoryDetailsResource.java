package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_HISTORY + "')")
public class VoucherHistoryDetailsResource {

  private final RepositoryEntityLinks repositoryEntityLinks;
  private final VhVoucherTypeRepo voucherTypeRepo;
  private final VhVoucherProviderRepo voucherProviderRepo;
  private final VhRechargeValueRepo rechargeValueRepo;
  private final VhVoucherHistoryRepo voucherHistoryRepo;

  @Autowired
  public VoucherHistoryDetailsResource(RepositoryEntityLinks repositoryEntityLinks,
      VhVoucherTypeRepo voucherTypeRepo, VhVoucherProviderRepo voucherProviderRepo,
      VhRechargeValueRepo rechargeValueRepo, VhVoucherHistoryRepo voucherHistoryRepo) {
    this.repositoryEntityLinks = repositoryEntityLinks;
    this.voucherTypeRepo = voucherTypeRepo;
    this.voucherProviderRepo = voucherProviderRepo;
    this.rechargeValueRepo = rechargeValueRepo;
    this.voucherHistoryRepo = voucherHistoryRepo;
  }

  @GetMapping("/voucherHistories/{voucherHistoryId}/voucherHistoryDetails")
  public ResponseEntity<VoucherHistoryDetails> getVoucherHistoryDetails(
      @PathVariable String voucherHistoryId) {

    VoucherHistory voucherHistory = voucherHistoryRepo.findById(voucherHistoryId).orElse(null);

    if (voucherHistory == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    VoucherHistoryDetails voucherHistoryDetails = new VoucherHistoryDetails();

    voucherHistoryDetails.add(
        linkTo(methodOn(VoucherHistoryDetailsResource.class).getVoucherHistoryDetails(voucherHistoryId))
            .withSelfRel());

    voucherHistoryDetails.add(
        linkTo(methodOn(VoucherHistoryDetailsResource.class).getVoucherHistoryDetails(voucherHistoryId))
            .withRel("voucherHistoryDetails"));

    Integer voucherTypeId = voucherHistory.getVoucherTypeId();
    if (voucherTypeId != null) {
      VoucherType voucherType = voucherTypeRepo.findById(voucherTypeId).orElse(null);
      voucherHistoryDetails.setVoucherType(voucherType);
      voucherHistoryDetails.add(repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherTypeId)
          .withRel("voucherType"));
    }
    Integer voucherProviderId = voucherHistory.getVoucherProviderId();
    if (voucherProviderId != null) {
      VoucherProvider voucherProvider = voucherProviderRepo.findById(voucherProviderId).orElse(null);
      voucherHistoryDetails.setVoucherProvider(voucherProvider);
      voucherHistoryDetails.add(
          repositoryEntityLinks.linkToItemResource(VoucherProvider.class, voucherProviderId)
              .withRel("voucherProvider"));
    }
    Integer rechargeValueId = voucherHistory.getRechargeValueId();
    if (rechargeValueId != null) {
      RechargeValue rechargeValue = rechargeValueRepo.findById(rechargeValueId).orElse(null);
      voucherHistoryDetails.setRechargeValue(rechargeValue);
      voucherHistoryDetails.add(
          repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValueId)
              .withRel("rechargeValue"));
    }

    return new ResponseEntity<>(voucherHistoryDetails, HttpStatus.OK);
  }

}
