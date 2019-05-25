package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationlistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationListOrderErrorHandler extends
    BatchOrderErrorHandler<BlockedVoucherActivationListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      BlockedVoucherActivationListOrder prodFileCreationBulkOrder) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER);
    return batchOrderHistory;
  }
}
