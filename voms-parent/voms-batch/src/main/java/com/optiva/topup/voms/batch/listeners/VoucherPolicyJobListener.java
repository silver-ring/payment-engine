package com.optiva.topup.voms.batch.listeners;

import com.optiva.topup.voms.common.document.VoucherPolicyHistory;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.common.UUIDs;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Value;

public abstract class VoucherPolicyJobListener implements JobListener, WithDocumentTopicsSupport {

  @Value("#{jobParameters[voucherPolicySchedule]}")
  private VoucherPolicySchedule voucherPolicySchedule;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    saveStartVoucherPolicyHistory();
    onBeforeJob(jobExecution);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    onAfterJob(jobExecution);
    List<Throwable> throwables = jobExecution.getAllFailureExceptions();
    if (throwables.isEmpty()) {
      saveEndVoucherPolicyHistory();
    } else {
      saveErrorVoucherPolicyHistory(throwables);
    }
  }

  private void saveStartVoucherPolicyHistory() {
    VoucherPolicyHistory voucherPolicyHistory = buildVoucherPolicyHistory(BatchJobHistoryStatus.STARTED);
    sendVoucherPolicyHistoryMessage(voucherPolicyHistory);
  }

  private void saveEndVoucherPolicyHistory() {
    VoucherPolicyHistory voucherPolicyHistory = buildVoucherPolicyHistory(BatchJobHistoryStatus.DONE);
    sendVoucherPolicyHistoryMessage(voucherPolicyHistory);
  }

  private void saveErrorVoucherPolicyHistory(List<Throwable> throwables) {
    VoucherPolicyHistory voucherPolicyHistory = buildVoucherPolicyHistory(BatchJobHistoryStatus.ERROR);
    voucherPolicyHistory.setErrorMessages(buildErrorMessages(throwables));
    sendVoucherPolicyHistoryMessage(voucherPolicyHistory);
  }

  private List<String> buildErrorMessages(List<Throwable> throwables) {
    return throwables.stream().map(Throwable::getMessage).collect(Collectors.toList());
  }

  private VoucherPolicyHistory buildVoucherPolicyHistory(BatchJobHistoryStatus batchJobHistoryStatus) {
    VoucherPolicyHistory voucherPolicyHistory = new VoucherPolicyHistory();
    voucherPolicyHistory.setId(UUIDs.randomBase64UUID());
    voucherPolicyHistory.setVoucherPolicyType(voucherPolicySchedule.getVoucherPolicyType());
    voucherPolicyHistory.setDescription(voucherPolicySchedule.getDescription());
    voucherPolicyHistory.setBatchJobHistoryStatus(batchJobHistoryStatus);
    voucherPolicyHistory.setEventTimestamp(LocalDateTime.now());
    return voucherPolicyHistory;
  }

  private void sendVoucherPolicyHistoryMessage(VoucherPolicyHistory voucherPolicyHistory) {
    sendDocumentMessage(voucherPolicyHistory);
  }

}
