package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusinglistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherUsingListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class PendingUsageVoucherUsingListOrderJobListener extends
    ListOrderJobListener<PendingUsageVoucherUsingListOrder> {

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_USING_LIST_ORDER);
    return batchOrderHistory;
  }

}
