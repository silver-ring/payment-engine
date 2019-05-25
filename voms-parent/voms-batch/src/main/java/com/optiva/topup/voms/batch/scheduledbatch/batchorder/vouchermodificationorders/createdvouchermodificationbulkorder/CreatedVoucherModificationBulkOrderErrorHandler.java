package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherModificationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<CreatedVoucherModificationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      CreatedVoucherModificationBulkOrder createdVouModBulkOrder) {
    batchOrderHistory.setStartSerialNumber(createdVouModBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(createdVouModBulkOrder.getEndSerialNumber());
    batchOrderHistory.setExpirationDate(createdVouModBulkOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(createdVouModBulkOrder.getRechargePeriod());
    if (createdVouModBulkOrder.getRechargeValue() != null) {
      batchOrderHistory.setRechargeValueId(createdVouModBulkOrder.getRechargeValue().getId());
    }
    if (createdVouModBulkOrder.getVoucherType() != null) {
      batchOrderHistory.setVoucherTypeId(createdVouModBulkOrder.getVoucherType().getId());
    }
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_MODIFICATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
