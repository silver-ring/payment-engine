package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockingbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.ActiveVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingBulkOrderErrorHandler extends
    BatchOrderErrorHandler<ActiveVoucherBlockingBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      ActiveVoucherBlockingBulkOrder activeVoucherBlockingBulkOrder) {
    batchOrderHistory.setStartSerialNumber(activeVoucherBlockingBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(activeVoucherBlockingBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.ACTIVE_VOUCHER_BLOCKING_BULK_ORDER);
    return batchOrderHistory;
  }

}
