package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.UsedVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class UsedVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void pendingUsageVoucherPostPersist(UsedVoucher usedVoucher) {
    recordVoucherHistory(usedVoucher);
  }

  @PostUpdate
  public void pendingUsageVoucherPostUpdate(UsedVoucher usedVoucher) {
    recordVoucherHistory(usedVoucher);
  }

  private void recordVoucherHistory(UsedVoucher usedVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(usedVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(usedVoucher.getSerialNumber());
    voucherHistory.setFinalRechargeValue(usedVoucher.getFinalRechargeValue());
    voucherHistory.setMsisdn(usedVoucher.getMsisdn());
    voucherHistory.setCcid(usedVoucher.getCcid());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.USED);
    sendDocumentMessage(voucherHistory);
  }

}

