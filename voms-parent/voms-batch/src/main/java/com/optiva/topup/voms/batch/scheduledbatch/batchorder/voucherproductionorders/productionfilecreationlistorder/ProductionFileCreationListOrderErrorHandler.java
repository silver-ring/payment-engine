package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class ProductionFileCreationListOrderErrorHandler extends
    BatchOrderErrorHandler<ProductionFileCreationListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      ProductionFileCreationListOrder listOrder) {
    batchOrderHistory.setLayoutId(listOrder.getLayoutId());
    batchOrderHistory.setVoucherPrinterId(listOrder.getVoucherPrinter().getId());

    batchOrderHistory.setBatchOrderType(BatchOrderType.PRODUCTION_FILE_CREATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
