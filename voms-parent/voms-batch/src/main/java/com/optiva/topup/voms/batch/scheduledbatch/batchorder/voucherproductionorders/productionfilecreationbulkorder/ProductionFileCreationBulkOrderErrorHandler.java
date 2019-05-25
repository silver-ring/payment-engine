package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class ProductionFileCreationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<ProductionFileCreationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      ProductionFileCreationBulkOrder bulkOrder) {
    batchOrderHistory.setStartSerialNumber(bulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(bulkOrder.getEndSerialNumber());
    batchOrderHistory.setLayoutId(bulkOrder.getLayoutId());
    batchOrderHistory.setVoucherPrinterId(bulkOrder.getVoucherPrinter().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PRODUCTION_FILE_CREATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
