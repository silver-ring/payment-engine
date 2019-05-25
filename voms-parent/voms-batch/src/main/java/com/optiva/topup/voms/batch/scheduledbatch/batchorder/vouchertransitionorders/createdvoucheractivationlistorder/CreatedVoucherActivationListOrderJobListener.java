package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.createdvoucheractivationlistorder;

import com.optiva.topup.voms.batch.listeners.ListOrderJobListener;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherActivationListOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class CreatedVoucherActivationListOrderJobListener extends
    ListOrderJobListener<CreatedVoucherActivationListOrder> {

  @Value("#{jobParameters[batchOrder]}")
  private CreatedVoucherActivationListOrder listOrder;

  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory) {
    batchOrderHistory.setVoucherProviderId(listOrder.getVoucherProvider().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.CREATED_VOUCHER_ACTIVATION_LIST_ORDER);
    return batchOrderHistory;
  }

}
