package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockinglistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.ActiveVoucherBlockingListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherBlockingListOrderErrorHandler extends
    BatchOrderErrorHandler<ActiveVoucherBlockingListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      ActiveVoucherBlockingListOrder listOrder) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.ACTIVE_VOUCHER_BLOCKING_LIST_ORDER);
    return batchOrderHistory;
  }

}
