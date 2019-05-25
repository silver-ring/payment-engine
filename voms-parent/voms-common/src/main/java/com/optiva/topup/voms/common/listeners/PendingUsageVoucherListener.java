package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class PendingUsageVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void pendingUsageVoucherPostPersist(PendingUsageVoucher pendingUsageVoucher) {
    recordVoucherHistory(pendingUsageVoucher);
  }

  @PostUpdate
  public void pendingUsageVoucherPostUpdate(PendingUsageVoucher pendingUsageVoucher) {
    recordVoucherHistory(pendingUsageVoucher);
  }

  private void recordVoucherHistory(PendingUsageVoucher pendingUsageVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(pendingUsageVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(pendingUsageVoucher.getSerialNumber());
    voucherHistory.setExpirationDate(pendingUsageVoucher.getExpirationDate());
    voucherHistory.setTransactionId(pendingUsageVoucher.getTransactionId());
    voucherHistory.setMsisdn(pendingUsageVoucher.getMsisdn());
    voucherHistory.setRechargePeriod(pendingUsageVoucher.getRechargePeriod());
    voucherHistory.setCcid(pendingUsageVoucher.getCcid());
    voucherHistory.setFinalRechargeValue(pendingUsageVoucher.getFinalRechargeValue());
    voucherHistory.setUsageRequestTime(pendingUsageVoucher.getUsageRequestTime());
    voucherHistory.setRechargeValueId(pendingUsageVoucher.getRechargeValue().getId());
    voucherHistory.setVoucherProviderId(pendingUsageVoucher.getVoucherProvider().getId());
    voucherHistory.setVoucherTypeId(pendingUsageVoucher.getVoucherType().getId());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.PENDING_USAGE);
    sendDocumentMessage(voucherHistory);

  }

}
