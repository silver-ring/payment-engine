package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BATCH_ORDER_HISTORY + "')")
public class BatchOrderHistoryDetailsResource {

  private final RepositoryEntityLinks repositoryEntityLinks;
  private final BohVoucherTypeRepo voucherTypeRepo;
  private final BohVoucherProviderRepo voucherProviderRepo;
  private final BohRechargeValueRepo rechargeValueRepo;
  private final BohBatchOrderHistoryRepo orderHistoryRepo;
  private final BohVoucherPrinterRepo voucherPrinterRepo;

  @Autowired
  public BatchOrderHistoryDetailsResource(RepositoryEntityLinks repositoryEntityLinks,
      BohVoucherTypeRepo voucherTypeRepo,
      BohVoucherProviderRepo voucherProviderRepo, BohRechargeValueRepo rechargeValueRepo,
      BohBatchOrderHistoryRepo orderHistoryRepo, BohVoucherPrinterRepo voucherPrinterRepo) {
    this.repositoryEntityLinks = repositoryEntityLinks;
    this.voucherTypeRepo = voucherTypeRepo;
    this.voucherProviderRepo = voucherProviderRepo;
    this.rechargeValueRepo = rechargeValueRepo;
    this.orderHistoryRepo = orderHistoryRepo;
    this.voucherPrinterRepo = voucherPrinterRepo;
  }

  @GetMapping("/batchOrderHistories/{batchOrderHistoryId}/batchOrderHistoryDetails")
  public ResponseEntity<BatchOrderHistoryDetails> getBatchOrderHistoryDetails(
      @PathVariable String batchOrderHistoryId) {

    BatchOrderHistory batchOrderHistory = orderHistoryRepo.findById(batchOrderHistoryId).orElse(null);

    if (batchOrderHistory == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    BatchOrderHistoryDetails batchOrderHistoryDetails = new BatchOrderHistoryDetails();
    Integer voucherTypeId = batchOrderHistory.getVoucherTypeId();

    batchOrderHistoryDetails.add(
        linkTo(
            methodOn(BatchOrderHistoryDetailsResource.class).getBatchOrderHistoryDetails(batchOrderHistoryId))
            .withSelfRel());

    batchOrderHistoryDetails.add(
        linkTo(
            methodOn(BatchOrderHistoryDetailsResource.class).getBatchOrderHistoryDetails(batchOrderHistoryId))
            .withRel("batchOrderHistoryDetails"));

    if (voucherTypeId != null) {
      VoucherType voucherType = voucherTypeRepo.findById(voucherTypeId).orElse(null);
      batchOrderHistoryDetails.setVoucherType(voucherType);
      batchOrderHistoryDetails
          .add(repositoryEntityLinks.linkToItemResource(VoucherType.class, voucherTypeId)
              .withRel("voucherType"));
    }

    Integer voucherProviderId = batchOrderHistory.getVoucherProviderId();
    if (voucherProviderId != null) {
      VoucherProvider voucherProvider = voucherProviderRepo.findById(voucherProviderId).orElse(null);
      batchOrderHistoryDetails.setVoucherProvider(voucherProvider);
      batchOrderHistoryDetails.add(
          repositoryEntityLinks.linkToItemResource(VoucherProvider.class, voucherProviderId)
              .withRel("voucherProvider"));
    }

    Integer rechargeValueId = batchOrderHistory.getRechargeValueId();
    if (rechargeValueId != null) {
      RechargeValue rechargeValue = rechargeValueRepo.findById(rechargeValueId).orElse(null);
      batchOrderHistoryDetails.setRechargeValue(rechargeValue);
      batchOrderHistoryDetails
          .add(repositoryEntityLinks.linkToItemResource(RechargeValue.class, rechargeValueId)
              .withRel("rechargeValue"));
    }

    Integer voucherPrinterId = batchOrderHistory.getVoucherPrinterId();
    if (voucherPrinterId != null) {
      VoucherPrinter voucherPrinter = voucherPrinterRepo.findById(voucherPrinterId).orElse(null);
      batchOrderHistoryDetails.setVoucherPrinter(voucherPrinter);
      batchOrderHistoryDetails.add(
          repositoryEntityLinks.linkToItemResource(VoucherPrinter.class, voucherPrinterId)
              .withRel("voucherPrinter"));
    }

    return new ResponseEntity<>(batchOrderHistoryDetails, HttpStatus.OK);
  }

}
