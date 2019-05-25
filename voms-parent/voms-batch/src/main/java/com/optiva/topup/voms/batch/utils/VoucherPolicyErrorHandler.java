package com.optiva.topup.voms.batch.utils;

import com.google.common.collect.Lists;
import com.optiva.topup.voms.common.document.VoucherPolicyHistory;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.elasticsearch.common.UUIDs;

public abstract class VoucherPolicyErrorHandler implements WithDocumentTopicsSupport {

  public void saveErrorVoucherPolicyHistory(VoucherPolicySchedule voucherPolicySchedule,
      Exception ex) {
    VoucherPolicyHistory voucherPolicyHistory = buildVoucherPolicyHistory(voucherPolicySchedule);
    voucherPolicyHistory.setErrorMessages(buildErrorMessages(ex));
    sendVoucherPolicyHistoryMessage(voucherPolicyHistory);
  }

  private List<String> buildErrorMessages(Exception ex) {
    return Lists.newArrayList(ex.getMessage());
  }

  private VoucherPolicyHistory buildVoucherPolicyHistory(VoucherPolicySchedule voucherPolicySchedule) {
    VoucherPolicyHistory voucherPolicyHistory = new VoucherPolicyHistory();
    voucherPolicyHistory.setId(UUIDs.randomBase64UUID());
    voucherPolicyHistory.setVoucherPolicyType(voucherPolicySchedule.getVoucherPolicyType());
    voucherPolicyHistory.setDescription(voucherPolicySchedule.getDescription());
    voucherPolicyHistory.setBatchJobHistoryStatus(BatchJobHistoryStatus.ERROR);
    voucherPolicyHistory.setEventTimestamp(LocalDateTime.now());
    return voucherPolicyHistory;
  }

  private void sendVoucherPolicyHistoryMessage(VoucherPolicyHistory voucherPolicyHistory) {
    sendDocumentMessage(voucherPolicyHistory);
  }

}
