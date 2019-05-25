package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationlistorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherActivationListOrderErrorHandler extends
    BatchOrderErrorHandler<CreatedVoucherActivationListOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      CreatedVoucherActivationListOrder listOrder) {
    batchOrderHistory.setVoucherProviderId(listOrder.getVoucherProvider().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_ACTIVATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
