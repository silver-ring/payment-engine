package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class DeletedVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void deletedVoucherPostPersist(DeletedVoucher deletedVoucher) {
    recordVoucherHistory(deletedVoucher);
  }

  @PostUpdate
  public void deletedVoucherPostUpdate(DeletedVoucher deletedVoucher) {
    recordVoucherHistory(deletedVoucher);
  }

  private void recordVoucherHistory(DeletedVoucher deletedVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(deletedVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(deletedVoucher.getSerialNumber());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.DELETED);
    sendDocumentMessage(voucherHistory);
  }

}
