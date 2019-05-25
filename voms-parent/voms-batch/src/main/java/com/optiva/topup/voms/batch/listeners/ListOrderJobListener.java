package com.optiva.topup.voms.batch.listeners;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.ListOrder;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Log4j2
public abstract class ListOrderJobListener<T extends ListOrder> extends BatchOrderJobListener<T> {

  @Value("#{jobParameters[orderFileResource]}")
  private Resource orderFileResource;

  @Override
  public void afterJob(JobExecution jobExecution) {
    deleteListOrderFile();
    super.afterJob(jobExecution);
  }

  protected BatchOrderHistory buildBatchOrderHistory(BatchJobHistoryStatus batchJobHistoryStatus) {
    BatchOrderHistory batchOrderHistory = super.buildBatchOrderHistory(batchJobHistoryStatus);
    batchOrderHistory.setOriginalFileName(batchOrder.getOriginalFileName());
    return batchOrderHistory;
  }

  private void deleteListOrderFile() {
    try {
      FileUtils.deleteQuietly(orderFileResource.getFile());
    } catch (IOException ex) {
      log.error(ex);
    }
  }

}
