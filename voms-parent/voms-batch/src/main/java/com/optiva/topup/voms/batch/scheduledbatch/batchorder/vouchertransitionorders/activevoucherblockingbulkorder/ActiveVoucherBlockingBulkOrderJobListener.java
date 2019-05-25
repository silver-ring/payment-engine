package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.activevoucherblockingbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.ActiveVoucherBlockingBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class ActiveVoucherBlockingBulkOrderJobListener extends
    BulkOrderJobListener<ActiveVoucherBlockingBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private ActiveVoucherBlockingBulkOrder activeVoucherBlockingBulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(activeVoucherBlockingBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(activeVoucherBlockingBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.ACTIVE_VOUCHER_BLOCKING_BULK_ORDER);
    return batchOrderHistory;
  }

}
