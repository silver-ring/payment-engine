package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class BlockedVoucherModificationBulkOrderJobListener extends
    BulkOrderJobListener<BlockedVoucherModificationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private BlockedVoucherModificationBulkOrder blockedVoucherModBulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(blockedVoucherModBulkOrder.getStartSerialNumber());
    batchOrderHistory.setEndSerialNumber(blockedVoucherModBulkOrder.getEndSerialNumber());
    batchOrderHistory.setExpirationDate(blockedVoucherModBulkOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(blockedVoucherModBulkOrder.getRechargePeriod());
    if (blockedVoucherModBulkOrder.getRechargeValue() != null) {
      batchOrderHistory.setRechargeValueId(blockedVoucherModBulkOrder.getRechargeValue().getId());
    }
    if (blockedVoucherModBulkOrder.getVoucherType() != null) {
      batchOrderHistory.setVoucherTypeId(blockedVoucherModBulkOrder.getVoucherType().getId());
    }
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_MODIFICATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
