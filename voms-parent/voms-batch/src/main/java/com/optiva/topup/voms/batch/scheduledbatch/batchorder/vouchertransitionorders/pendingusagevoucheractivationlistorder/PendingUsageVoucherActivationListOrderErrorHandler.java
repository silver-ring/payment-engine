package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucheractivationlistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherActivationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherActivationListOrderErrorHandler extends
    BatchOrderErrorHandler<PendingUsageVoucherActivationListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      PendingUsageVoucherActivationListOrder prodFileCreationBulkOrder) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.PENDING_USAGE_VOUCHER_ACTIVATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
