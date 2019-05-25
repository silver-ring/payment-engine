package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class ProductionFileCreationBulkOrderJobListener extends
    BulkOrderJobListener<ProductionFileCreationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private ProductionFileCreationBulkOrder prodFileCreationBulkOrder;
  @Value("#{jobParameters[productionFileResource]}")
  private Resource productionFileResource;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setProductionFileName(productionFileResource.getFilename());
    batchOrderHistory.setStartSerialNumber(prodFileCreationBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(prodFileCreationBulkOrder.getEndSerialNumber());
    batchOrderHistory.setLayoutId(prodFileCreationBulkOrder.getLayoutId());
    batchOrderHistory.setVoucherPrinterId(prodFileCreationBulkOrder.getVoucherPrinter().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PRODUCTION_FILE_CREATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
