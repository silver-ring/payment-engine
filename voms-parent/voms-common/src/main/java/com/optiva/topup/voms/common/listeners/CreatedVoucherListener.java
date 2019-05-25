package com.optiva.topup.voms.common.listeners;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherStatus;
import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.time.LocalDateTime;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.elasticsearch.common.UUIDs;

public class CreatedVoucherListener implements WithDocumentTopicsSupport {

  @PostPersist
  public void createdVoucherPostPersist(CreatedVoucher createdVoucher) {
    recordVoucherHistory(createdVoucher);
  }

  @PostUpdate
  public void createdVoucherPostUpdate(CreatedVoucher createdVoucher) {
    recordVoucherHistory(createdVoucher);
  }

  private void recordVoucherHistory(CreatedVoucher createdVoucher) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);

    VoucherHistory voucherHistory = new VoucherHistory();
    voucherHistory.setId(UUIDs.randomBase64UUID());
    voucherHistory.setVoucherId(voucherIdCipher.encrypt(createdVoucher.getVoucherId()));
    voucherHistory.setSerialNumber(createdVoucher.getSerialNumber());
    voucherHistory.setExpirationDate(createdVoucher.getExpirationDate());
    voucherHistory.setRechargePeriod(createdVoucher.getRechargePeriod());
    voucherHistory.setRechargeValueId(createdVoucher.getRechargeValue().getId());
    voucherHistory.setVoucherTypeId(createdVoucher.getVoucherType().getId());
    voucherHistory.setEventTimestamp(LocalDateTime.now());
    voucherHistory.setVoucherStatus(VoucherStatus.CREATED);
    sendDocumentMessage(voucherHistory);
  }

}
