package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationlistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherModificationListOrderErrorHandler extends
    BatchOrderErrorHandler<BlockedVoucherModificationListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      BlockedVoucherModificationListOrder listOrder) {
    batchOrderHistory.setExpirationDate(listOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(listOrder.getRechargePeriod());
    if (listOrder.getRechargeValue() != null) {
      batchOrderHistory.setRechargeValueId(listOrder.getRechargeValue().getId());
    }
    if (listOrder.getVoucherType() != null) {
      batchOrderHistory.setVoucherTypeId(listOrder.getVoucherType().getId());
    }
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_MODIFICATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
