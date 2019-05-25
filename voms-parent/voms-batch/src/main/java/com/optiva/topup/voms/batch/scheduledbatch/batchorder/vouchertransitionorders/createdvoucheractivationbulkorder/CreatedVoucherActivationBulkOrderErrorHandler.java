package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherActivationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<CreatedVoucherActivationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      CreatedVoucherActivationBulkOrder createdVouActBulkOrder) {
    batchOrderHistory.setStartSerialNumber(createdVouActBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(createdVouActBulkOrder.getEndSerialNumber());
    batchOrderHistory.setVoucherPrinterId(createdVouActBulkOrder.getVoucherProvider().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }
}
