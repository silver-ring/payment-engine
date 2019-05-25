package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<PendingUsageVoucherActivationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      PendingUsageVoucherActivationBulkOrder bulkOrder) {
    batchOrderHistory
        .setPendingRangeStartTime(bulkOrder.getPendingRangeStartTime());
    batchOrderHistory.setPendingRangeEndTime(bulkOrder.getPendingRangeEndTime());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
