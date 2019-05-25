package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucherdestroybulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class CreatedVoucherDestroyBulkOrderJobListener extends
    BulkOrderJobListener<CreatedVoucherDestroyBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherDestroyBulkOrder createdVoucherDestroyBulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(createdVoucherDestroyBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(createdVoucherDestroyBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATE_VOUCHER_DESTROY_BULK_ORDER);
    return batchOrderHistory;
  }

}
