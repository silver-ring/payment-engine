package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import com.optiva.topup.voms.batch.listeners.BulkOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class BlockedVoucherActivationBulkOrderJobListener extends
    BulkOrderJobListener<BlockedVoucherActivationBulkOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private BlockedVoucherActivationBulkOrder blockedVouActivationBulkOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setStartSerialNumber(blockedVouActivationBulkOrder.getStartSerialNumber());
    batchOrderHistory.setStartSerialNumber(blockedVouActivationBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
