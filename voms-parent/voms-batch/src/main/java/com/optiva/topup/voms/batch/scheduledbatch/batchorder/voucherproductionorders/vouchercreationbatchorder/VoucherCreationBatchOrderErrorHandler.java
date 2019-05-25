package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.vouchercreationbatchorder;

import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.VoucherCreationBatchOrder;
import com.optiva.topup.voms.common.types.BatchOrderType;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreationBatchOrderErrorHandler extends BatchOrderErrorHandler<VoucherCreationBatchOrder> {

  @Override
  protected BatchOrderHistory buildBatchOrderHistory(BatchOrderHistory batchOrderHistory,
      VoucherCreationBatchOrder voucherCreationBatchOrder) {
    batchOrderHistory.setNumberOfVouchers(voucherCreationBatchOrder.getNumberOfVouchers());
    batchOrderHistory.setExpirationDate(voucherCreationBatchOrder.getExpirationDate());
    batchOrderHistory.setRechargePeriod(voucherCreationBatchOrder.getRechargePeriod());
    batchOrderHistory.setRechargeValueId(voucherCreationBatchOrder.getRechargeValue().getId());
    batchOrderHistory.setVoucherTypeId(voucherCreationBatchOrder.getVoucherType().getId());
    batchOrderHistory.setBatchOrderType(BatchOrderType.VOUCHER_CREATION_BATCH_ORDER);
    return batchOrderHistory;
  }

}
