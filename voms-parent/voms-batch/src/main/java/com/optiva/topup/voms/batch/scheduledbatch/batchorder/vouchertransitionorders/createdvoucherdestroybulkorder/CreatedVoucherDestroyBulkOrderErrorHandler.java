package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroybulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherDestroyBulkOrderErrorHandler extends
    BatchOrderErrorHandler<CreatedVoucherDestroyBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      CreatedVoucherDestroyBulkOrder createdVoucherDestroyBulkOrder) {
    batchOrderHistory.setStartSerialNumber(createdVoucherDestroyBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(createdVoucherDestroyBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATE_VOUCHER_DESTROY_BULK_ORDER);
    return batchOrderHistory;
  }

}
