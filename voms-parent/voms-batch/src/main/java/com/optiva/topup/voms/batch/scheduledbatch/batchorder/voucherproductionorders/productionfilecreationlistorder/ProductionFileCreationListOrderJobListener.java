package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class ProductionFileCreationListOrderJobListener extends
    ListOrderJobListener<ProductionFileCreationListOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private ProductionFileCreationListOrder prodFileCreationListOrder;

  @Value("#{jobParameters[productionFileResource]}")
  private Resource productionFileResource;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setProductionFileName(productionFileResource.getFilename());
    batchOrderHistory.setLayoutId(prodFileCreationListOrder.getLayoutId());
    batchOrderHistory.setVoucherPrinterId(prodFileCreationListOrder.getVoucherPrinter().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PRODUCTION_FILE_CREATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
