package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class PendingUsageVoucherActivationBulkOrderJobListener extends
    BulkOrderJobListener<PendingUsageVoucherActivationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private PendingUsageVoucherActivationBulkOrder pendUsageVouActBlkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory
        .setPendingRangeStartTime(pendUsageVouActBlkOrder.getPendingRangeStartTime());
    batchOrderHistory.setPendingRangeEndTime(pendUsageVouActBlkOrder.getPendingRangeEndTime());
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
