package com.optiva.topup.voms.soap.voucherconsumption;

import static com.optiva.topup.voms.soap.elements.ElementsConfigParametersHelper.VOMS_NAME_SPACE;

import com.optiva.topup.voms.common.document.RechargeHistory;
import com.optiva.topup.voms.common.security.ApiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.RechargeHistoryStatus;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import com.optiva.topup.voms.soap.elements.RechargeConfirmationRequest;
import com.optiva.topup.voms.soap.elements.RechargeConfirmationResponse;
import com.optiva.topup.voms.soap.elements.RechargeRequest;
import com.optiva.topup.voms.soap.elements.RechargeResponse;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.common.UUIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@PreAuthorize("hasAuthority('" + ApiUserAuthoritiesHelper.VOUCHER_CONSUMPTION + "')")
@Log4j2
public class VoucherConsumptionWs implements WithDocumentTopicsSupport {

  private final RechargeService rechargeService;
  private final RechargeConfirmationService rechargeConfirmationService;
  private final VoucherCipher voucherCipher;

  @Autowired
  public VoucherConsumptionWs(RechargeService rechargeService,
      RechargeConfirmationService rechargeConfirmationService, VoucherCipher voucherCipher) {
    this.rechargeService = rechargeService;
    this.rechargeConfirmationService = rechargeConfirmationService;
    this.voucherCipher = voucherCipher;
  }

  @PayloadRoot(namespace = VOMS_NAME_SPACE, localPart = "rechargeRequest")
  @ResponsePayload
  public RechargeResponse recharge(@RequestPayload RechargeRequest consumptionRequest) {

    RechargeHistory rechargeHistory = new RechargeHistory();
    try {
      rechargeHistory.setCcId(consumptionRequest.getCcId());
      rechargeHistory.setMsisdn(consumptionRequest.getMsisdn());
      rechargeHistory.setMsisdnProviderId(consumptionRequest.getMsisdnProviderId());
      rechargeHistory.setTransactionId(consumptionRequest.getTransactionId());
      rechargeHistory.setVoucherId(voucherCipher.encrypt(consumptionRequest.getVoucherId()));

      RechargeResponse rechargeResponse = rechargeService.rechargeRequest(consumptionRequest);
      rechargeHistory.setAccountId(rechargeResponse.getAccountId());
      rechargeHistory.setAmount(rechargeResponse.getAmount());
      rechargeHistory.setBalanceExtension(rechargeResponse.getBalanceExtension());
      rechargeHistory.setCurrency(rechargeResponse.getCurrency());
      rechargeHistory.setErrorCode(rechargeResponse.getErrorCode());
      rechargeHistory.setProviderIdVoucher(rechargeResponse.getProviderIdVoucher());
      rechargeHistory.setSerialNumber(rechargeResponse.getSerialNo());
      rechargeHistory.setVoucherExpiryDate(rechargeResponse.getVoucherExpiryDate());
      rechargeHistory.setVoucherType(rechargeResponse.getVoucherType());
      rechargeHistory.setRechargeHistoryStatus(RechargeHistoryStatus.REQUESTED);
      return rechargeResponse;
    } catch (Exception ex) {
      rechargeHistory.setRechargeHistoryStatus(RechargeHistoryStatus.REQUEST_ERROR);
      rechargeHistory.setErrorMessage(ex.getMessage());
      log.error(ex);
      throw ex;
    } finally {
      rechargeHistory.setId(UUIDs.randomBase64UUID());
      rechargeHistory.setEventTimestamp(LocalDateTime.now());
      sendDocumentMessage(rechargeHistory);
    }
  }

  @PayloadRoot(namespace = VOMS_NAME_SPACE, localPart = "rechargeConfirmationRequest")
  @ResponsePayload
  public RechargeConfirmationResponse rechargeConfirmation(
      @RequestPayload RechargeConfirmationRequest rechargeConfirmationRequest) {

    RechargeHistory rechargeHistory = new RechargeHistory();
    try {
      rechargeHistory.setMsisdn(rechargeConfirmationRequest.getMsisdn());
      rechargeHistory.setTransactionId(rechargeConfirmationRequest.getTransactionId());
      rechargeHistory.setVoucherId(voucherCipher.encrypt(rechargeConfirmationRequest.getVoucherId()));
      RechargeConfirmationResponse rechargeConfirmationResponse =
          rechargeConfirmationService.rechargeConfirmationRequest(rechargeConfirmationRequest);
      rechargeHistory.setErrorCode(rechargeConfirmationResponse.getErrorCode());
      rechargeHistory.setRechargeHistoryStatus(RechargeHistoryStatus.CONFIRMED);
      return rechargeConfirmationResponse;
    } catch (Exception ex) {
      rechargeHistory.setErrorMessage(ex.getMessage());
      rechargeHistory.setRechargeHistoryStatus(RechargeHistoryStatus.CONFIRMATION_ERROR);
      log.error(ex);
      throw ex;
    } finally {
      rechargeHistory.setId(UUIDs.randomBase64UUID());
      rechargeHistory.setEventTimestamp(LocalDateTime.now());
      sendDocumentMessage(rechargeHistory);
    }
  }

}
