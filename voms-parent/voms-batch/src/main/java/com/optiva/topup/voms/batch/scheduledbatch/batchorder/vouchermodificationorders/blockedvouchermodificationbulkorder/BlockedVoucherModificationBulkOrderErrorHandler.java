package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherModificationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<BlockedVoucherModificationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      BlockedVoucherModificationBulkOrder blockedVoucherModBulkOrder) {
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
