package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class ActiveVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void activeVoucherPostPersist(ActiveVoucher activeVoucher) {
    recordVoucherHistory(activeVoucher);
  }

  @PostUpdate
  public void activeVoucherPostUpdate(ActiveVoucher activeVoucher) {
    recordVoucherHistory(activeVoucher);
  }

  private void recordVoucherHistory(ActiveVoucher activeVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(activeVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(activeVoucher.getSerialNumber());
    voucherHistory.setExpirationDate(activeVoucher.getExpirationDate());
    voucherHistory.setRechargePeriod(activeVoucher.getRechargePeriod());
    voucherHistory.setRechargeValueId(activeVoucher.getRechargeValue().getId());
    voucherHistory.setVoucherProviderId(activeVoucher.getVoucherProvider().getId());
    voucherHistory.setVoucherTypeId(activeVoucher.getVoucherType().getId());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.ACTIVE);
    sendDocumentMessage(voucherHistory);
  }

}
