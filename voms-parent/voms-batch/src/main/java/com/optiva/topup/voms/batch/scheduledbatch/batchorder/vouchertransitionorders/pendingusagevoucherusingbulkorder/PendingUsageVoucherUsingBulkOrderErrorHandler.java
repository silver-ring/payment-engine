package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusingbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherUsingBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherUsingBulkOrderErrorHandler extends
    BatchOrderErrorHandler<PendingUsageVoucherUsingBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      PendingUsageVoucherUsingBulkOrder bulkOrder) {
    batchOrderHistory.setPendingRangeStartTime(bulkOrder.getPendingRangeStartTime());
    batchOrderHistory.setPendingRangeEndTime(bulkOrder.getPendingRangeEndTime());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_USING_BULK_ORDER);
    return batchOrderHistory;
  }

}
