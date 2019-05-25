package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroylistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class CreatedVoucherDestroyListOrderJobListener extends
    ListOrderJobListener<CreatedVoucherDestroyListOrder> {

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_DESTROY_LIST_ORDER);
    return batchOrderHistory;
  }

}
