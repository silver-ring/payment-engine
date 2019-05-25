package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroylistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyListOrderErrorHandler extends
    BatchOrderErrorHandler<CreatedVoucherDestroyListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      CreatedVoucherDestroyListOrder prodFileCreationBulkOrder) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_DESTROY_LIST_ORDER);
    return batchOrderHistory;
  }

}
