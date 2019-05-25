package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockingbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class PendingUsageVoucherBlockingBulkOrderJobListener extends
    BulkOrderJobListener<PendingUsageVoucherBlockingBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private PendingUsageVoucherBlockingBulkOrder bulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory
        .setPendingRangeStartTime(bulkOrder.getPendingRangeStartTime());
    batchOrderHistory.setPendingRangeEndTime(bulkOrder.getPendingRangeEndTime());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_BLOCKING_BULK_ORDER);
    return batchOrderHistory;
  }

}
