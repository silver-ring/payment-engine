package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.entities.voucherconfig.MsisdnProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.OcsAccount;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.utils.RechargeValueCalculator;
import com.optiva.topup.voms.soap.elements.RechargeRequest;
import com.optiva.topup.voms.soap.elements.RechargeResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RechargeService {

  private final RActiveVoucherRepo activeVoucherRepo;
  private final RMsisdnProviderRepo msisdnProviderRepo;
  private final RConsumableVoucherTypeRepo consumableVoucherTypeRepo;
  private final RechargeValueCalculator rechargeValueCalculator;
  private final RPendingUsageVoucherRepo pendingUsageVoucherRepo;

  @Autowired
  public RechargeService(RActiveVoucherRepo activeVoucherRepo, RMsisdnProviderRepo msisdnProviderRepo,
      RConsumableVoucherTypeRepo consumableVoucherTypeRepo, RechargeValueCalculator rechargeValueCalculator,
      RPendingUsageVoucherRepo pendingUsageVoucherRepo) {
    this.activeVoucherRepo = activeVoucherRepo;
    this.msisdnProviderRepo = msisdnProviderRepo;
    this.consumableVoucherTypeRepo = consumableVoucherTypeRepo;
    this.rechargeValueCalculator = rechargeValueCalculator;
    this.pendingUsageVoucherRepo = pendingUsageVoucherRepo;
  }

  @Transactional
  public RechargeResponse rechargeRequest(RechargeRequest rechargeRequest) {

    ActiveVoucher activeVoucher = activeVoucherRepo.findByVoucherId(rechargeRequest.getVoucherId());
    if (activeVoucher == null) {
      throw new VoucherNotFoundException();
    }

    LocalDate todayDate = LocalDate.now();
    if (activeVoucher.getExpirationDate().isBefore(todayDate)) {
      throw new VoucherExpiredException();
    }

    MsisdnProvider msisdnProvider = msisdnProviderRepo.findByIdAtIn(rechargeRequest.getMsisdnProviderId());

    if (msisdnProvider == null) {
      throw new MsisdnProviderNotFoundException();
    }

    VoucherProvider voucherProvider = activeVoucher.getVoucherProvider();
    VoucherType voucherType = activeVoucher.getVoucherType();
    if (!consumableVoucherTypeRepo
        .existsByMsisdnProviderAndVoucherProviderAndVoucherType(msisdnProvider, voucherProvider,
            voucherType)) {
      throw new VoucherIsNotConsumableException();
    }

    double finalRechargeValue = rechargeValueCalculator.calculateFinalValue(activeVoucher.getRechargeValue());

    PendingUsageVoucher pendingUsageVoucher = new PendingUsageVoucher();
    pendingUsageVoucher.setVoucherId(activeVoucher.getVoucherId());
    pendingUsageVoucher.setExpirationDate(activeVoucher.getExpirationDate());
    pendingUsageVoucher.setRechargePeriod(activeVoucher.getRechargePeriod());
    pendingUsageVoucher.setRechargeValue(activeVoucher.getRechargeValue());
    pendingUsageVoucher.setSerialNumber(activeVoucher.getSerialNumber());
    pendingUsageVoucher.setVoucherProvider(activeVoucher.getVoucherProvider());
    pendingUsageVoucher.setVoucherType(activeVoucher.getVoucherType());
    pendingUsageVoucher.setUsageRequestTime(LocalDateTime.now());
    pendingUsageVoucher.setUsageRequestTime(LocalDateTime.now());
    pendingUsageVoucher.setTransactionId(rechargeRequest.getTransactionId());
    pendingUsageVoucher.setFinalRechargeValue(finalRechargeValue);
    pendingUsageVoucher.setMsisdn(rechargeRequest.getMsisdn());
    pendingUsageVoucher.setCcid(rechargeRequest.getCcId());

    RechargeResponse consumptionResponse = new RechargeResponse();
    consumptionResponse.setVoucherType(voucherType.getId());

    consumptionResponse.setVoucherExpiryDate(
        (int) (activeVoucher.getExpirationDate().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
            / 1000));
    consumptionResponse.setSerialNo(activeVoucher.getSerialNumber());
    consumptionResponse.setProviderIdVoucher(voucherProvider.getIdAtIn());
    consumptionResponse.setErrorCode(0);
    consumptionResponse.setBalanceExtension(activeVoucher.getRechargePeriod());
    consumptionResponse.setAmount(finalRechargeValue);

    OcsAccount ocsAccount = activeVoucher.getRechargeValue().getOcsAccount();
    consumptionResponse.setCurrency(ocsAccount.getCurrency().getUnit());
    consumptionResponse.setAccountId(ocsAccount.getIdAtOcs());

    activeVoucherRepo.delete(activeVoucher);
    pendingUsageVoucherRepo.save(pendingUsageVoucher);

    return consumptionResponse;
  }

}
