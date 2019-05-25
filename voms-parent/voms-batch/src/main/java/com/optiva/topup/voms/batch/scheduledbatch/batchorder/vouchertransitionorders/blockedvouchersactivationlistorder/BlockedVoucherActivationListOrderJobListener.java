package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationlistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class BlockedVoucherActivationListOrderJobListener extends
    ListOrderJobListener<BlockedVoucherActivationListOrder> {

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
