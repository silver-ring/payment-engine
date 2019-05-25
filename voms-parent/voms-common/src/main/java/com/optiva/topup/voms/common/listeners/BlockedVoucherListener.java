package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class BlockedVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void blockedVoucherPostPersist(BlockedVoucher blockedVoucher) {
    recordVoucherHistory(blockedVoucher);
  }

  @PostUpdate
  public void blockedVoucherPostUpdate(BlockedVoucher blockedVoucher) {
    recordVoucherHistory(blockedVoucher);
  }

  private void recordVoucherHistory(BlockedVoucher blockedVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(blockedVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(blockedVoucher.getSerialNumber());
    voucherHistory.setExpirationDate(blockedVoucher.getExpirationDate());
    voucherHistory.setRechargePeriod(blockedVoucher.getRechargePeriod());
    voucherHistory.setRechargeValueId(blockedVoucher.getRechargeValue().getId());
    voucherHistory.setVoucherProviderId(blockedVoucher.getVoucherProvider().getId());
    voucherHistory.setVoucherTypeId(blockedVoucher.getVoucherType().getId());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.BLOCKED);

    sendDocumentMessage(voucherHistory);
  }

}
