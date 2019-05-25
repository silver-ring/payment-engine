package com.optiva.topup.voms.batch.utils;

import com.google.common.collect.Lists;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.elasticsearch.common.UUIDs;

public abstract class BatchOrderErrorHandler<T extends BatchOrder> implements WithDocumentTopicsSupport {

  public void saveErrorBatchOrderHistory(T batchOrder, Exception ex) {
    BatchOrderHistory batchOrderHistory = buildBatchOrderHistory(batchOrder);
    batchOrderHistory.setErrorMessages(buildErrorMessages(ex));
    sendBatchOrderHistoryMessage(batchOrderHistory);
  }

  protected BatchOrderHistory buildBatchOrderHistory(T batchOrder) {
    BatchOrderHistory batchOrderHistory = new BatchOrderHistory();
    batchOrderHistory.setId(UUIDs.randomBase64UUID());
    batchOrderHistory.setTagId(batchOrder.getTagId());
    batchOrderHistory.setExecutionTime(batchOrder.getExecutionTime());
    batchOrderHistory.setBatchJobHistoryStatus(BatchJobHistoryStatus.ERROR);
    batchOrderHistory.setEventTimestamp(LocalDateTime.now());
    return buildBatchOrderHistory(batchOrderHistory, batchOrder);
  }

  protected abstract BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      T batchOrder);

  private List<String> buildErrorMessages(Exception ex) {
    return Lists.newArrayList(ex.getMessage());
  }

  private void sendBatchOrderHistoryMessage(BatchOrderHistory batchOrderHistory) {
    sendDocumentMessage(batchOrderHistory);
  }

}
