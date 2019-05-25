package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockingbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherBlockingBulkOrderErrorHandler extends
    BatchOrderErrorHandler<PendingUsageVoucherBlockingBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      PendingUsageVoucherBlockingBulkOrder bulkOrder) {
    batchOrderHistory
        .setPendingRangeStartTime(bulkOrder.getPendingRangeStartTime());
    batchOrderHistory.setPendingRangeEndTime(bulkOrder.getPendingRangeEndTime());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_BLOCKING_BULK_ORDER);
    return batchOrderHistory;
  }

}
