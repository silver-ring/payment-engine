package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class CreatedVoucherModificationBulkOrderJobListener extends
    BulkOrderJobListener<CreatedVoucherModificationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherModificationBulkOrder bulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(bulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(bulkOrder.getEndSerialNumber());
    batchOrderHistory.setExpirationDate(bulkOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(bulkOrder.getRechargePeriod());
    if (bulkOrder.getRechargeValue() != null) {
      batchOrderHistory.setRechargeValueId(bulkOrder.getRechargeValue().getId());
    }
    if (bulkOrder.getVoucherType() != null) {
      batchOrderHistory.setVoucherTypeId(bulkOrder.getVoucherType().getId());
    }
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_MODIFICATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
