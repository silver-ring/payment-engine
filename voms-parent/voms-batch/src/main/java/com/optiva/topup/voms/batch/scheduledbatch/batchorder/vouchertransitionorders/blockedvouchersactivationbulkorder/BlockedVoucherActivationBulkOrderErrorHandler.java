package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.blockedvouchersactivationbulkorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationBulkOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherActivationBulkOrderErrorHandler extends
    BatchOrderErrorHandler<BlockedVoucherActivationBulkOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      BlockedVoucherActivationBulkOrder blockedVouActivationBulkOrder) {
    batchOrderHistory.setStartSerialNumber(blockedVouActivationBulkOrder.getStartSerialNumber());
    batchOrderHistory.setStartSerialNumber(blockedVouActivationBulkOrder.getEndSerialNumber());
    batchOrderHistory.setBatchOrderType(BatchOrderType.BLOCKED_VOUCHER_ACTIVATION_BULK_ORDER);
    return batchOrderHistory;
  }

}
