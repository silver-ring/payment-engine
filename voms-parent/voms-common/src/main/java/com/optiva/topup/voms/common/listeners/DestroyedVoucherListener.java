package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class DestroyedVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void destroyedVoucherPostPersist(DestroyedVoucher destroyedVoucher) {
    recordVoucherHistory(destroyedVoucher);
  }

  @PostUpdate
  public void destroyedVoucherPostUpdate(DestroyedVoucher destroyedVoucher) {
    recordVoucherHistory(destroyedVoucher);
  }

  private void recordVoucherHistory(DestroyedVoucher destroyedVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);
    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(destroyedVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(destroyedVoucher.getSerialNumber());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.DESTROYED);
    sendDocumentMessage(voucherHistory);
  }

}
