package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchertransitionorders.pendingusagevoucherusinglistorder;

import com.optiva.topup.voms.batch.jobitemssupport.ListOrderItemStreamReader;
import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class PendingUsageVoucherUsingListOrderStreamReader extends
    ListOrderItemStreamReader<PendingUsageVoucher> {

}
