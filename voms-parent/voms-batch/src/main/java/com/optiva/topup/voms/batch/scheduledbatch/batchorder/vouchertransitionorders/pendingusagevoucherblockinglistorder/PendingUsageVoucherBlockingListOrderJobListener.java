package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherblockinglistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class PendingUsageVoucherBlockingListOrderJobListener extends
    ListOrderJobListener<PendingUsageVoucherBlockingListOrder> {

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER);
    return batchOrderHistory;
  }

}
