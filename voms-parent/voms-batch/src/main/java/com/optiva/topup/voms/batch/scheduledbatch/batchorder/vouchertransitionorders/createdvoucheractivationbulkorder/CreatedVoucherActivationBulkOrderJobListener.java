package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class CreatedVoucherActivationBulkOrderJobListener extends
    BulkOrderJobListener<CreatedVoucherActivationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherActivationBulkOrder bulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(bulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(bulkOrder.getEndSerialNumber());
    batchOrderHistory.setVoucherPrinterId(bulkOrder.getVoucherProvider().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
