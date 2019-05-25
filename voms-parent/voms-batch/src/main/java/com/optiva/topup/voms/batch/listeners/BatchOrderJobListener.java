package com.optiva.topup.voms.batch.listeners;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.common.UUIDs;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class BatchOrderJobListener<T extends BatchOrder> implements JobListener,
    WithDocumentTopicsSupport {

  @Value("#{jobParameters[batchOrder]}")
  protected T batchOrder;
  @Autowired
  private BatchOrderDao batchOrderDao;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    deleteOrder();
    saveStartBatchOrderHistory();
    onBeforeJob(jobExecution);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    onAfterJob(jobExecution);
    List<Throwable> throwables = jobExecution.getAllFailureExceptions();
    if (throwables.isEmpty()) {
      saveDoneBatchOrderHistory();
    } else {
      saveErrorBatchOrderHistory(throwables);
    }
  }

  private void deleteOrder() {
    batchOrderDao.deleteOrder(batchOrder);
  }

  private void saveStartBatchOrderHistory() {
    BatchOrderHistory batchOrderHistory = buildBatchOrderHistory(BatchJobHistoryStatus.STARTED);
    sendBatchOrderHistoryMessage(batchOrderHistory);
  }

  private void saveDoneBatchOrderHistory() {
    BatchOrderHistory batchOrderHistory = buildBatchOrderHistory(BatchJobHistoryStatus.DONE);
    sendBatchOrderHistoryMessage(batchOrderHistory);
  }

  public void saveErrorBatchOrderHistory(List<Throwable> throwables) {
    BatchOrderHistory batchOrderHistory = buildBatchOrderHistory(BatchJobHistoryStatus.ERROR);
    batchOrderHistory.setErrorMessages(buildErrorMessages(throwables));
    sendBatchOrderHistoryMessage(batchOrderHistory);
  }

  protected BatchOrderHistory buildBatchOrderHistory(BatchJobHistoryStatus batchJobHistoryStatus) {
    BatchOrderHistory batchOrderHistory = new BatchOrderHistory();
    batchOrderHistory.setId(UUIDs.randomBase64UUID());
    batchOrderHistory.setTagId(batchOrder.getTagId());
    batchOrderHistory.setExecutionTime(batchOrder.getExecutionTime());
    batchOrderHistory.setBatchJobHistoryStatus(batchJobHistoryStatus);
    batchOrderHistory.setEventTimestamp(LocalDateTime.now());
    return buildBatchOrderHistory(batchOrderHistory);
  }

  protected abstract BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchJobHistoryStatus);

  private List<String> buildErrorMessages(List<Throwable> throwables) {
    return throwables.stream().map(Throwable::getMessage).collect(Collectors.toList());
  }

  private void sendBatchOrderHistoryMessage(BatchOrderHistory batchOrderHistory) {
    sendDocumentMessage(batchOrderHistory);
  }

}
