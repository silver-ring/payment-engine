package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.soap.elements.RechargeConfirmationRequest;
import com.optiva.topup.voms.soap.elements.RechargeConfirmationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RechargeConfirmationService {

  private final RPendingUsageVoucherRepo pendingUsageVoucherRepo;
  private final RUsedVoucherRepo usedVoucherRepo;

  @Autowired
  public RechargeConfirmationService(RPendingUsageVoucherRepo pendingUsageVoucherRepo,
      RUsedVoucherRepo usedVoucherRepo) {
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
    this.usedVoucherRepo = usedVoucherRepo;
  }

  RechargeConfirmationResponse rechargeConfirmationRequest(
      RechargeConfirmationRequest rechargeConfirmationRequest) {

    PendingUsageVoucher pendingUsageVoucher = pendingUsageVoucherRepo
        .findByVoucherId(rechargeConfirmationRequest.getVoucherId())
        .orElseThrow(VoucherNotFoundException::new);

    UsedVoucher usedVoucher = new UsedVoucher();
    usedVoucher.setVoucherId(pendingUsageVoucher.getVoucherId());
    usedVoucher.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    usedVoucher.setFinalRechargeValue(pendingUsageVoucher.getFinalRechargeValue());
    usedVoucher.setMsisdn(pendingUsageVoucher.getMsisdn());
    usedVoucher.setCcid(pendingUsageVoucher.getCcid());

    pendingUsageVoucherRepo.delete(pendingUsageVoucher);
    usedVoucherRepo.save(usedVoucher);

    RechargeConfirmationResponse rechargeConfirmationResponse = new RechargeConfirmationResponse();
    rechargeConfirmationResponse.setErrorCode(0);

    return rechargeConfirmationResponse;
  }

}
