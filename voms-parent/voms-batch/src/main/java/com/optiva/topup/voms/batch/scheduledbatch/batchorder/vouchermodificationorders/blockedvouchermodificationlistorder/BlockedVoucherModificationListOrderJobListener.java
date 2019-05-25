package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.blockedvouchermodificationlistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.BlockedVoucherModificationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class BlockedVoucherModificationListOrderJobListener extends
    ListOrderJobListener<BlockedVoucherModificationListOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private BlockedVoucherModificationListOrder listOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
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
