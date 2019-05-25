package com.optiva.topup.voms.batch.scheduledbatch.batchorder.vouchermodificationorders.createdvouchermodificationlistorder;

import com.optiva.topup.voms.batch.jobitemssupport.ListOrderItemStreamReader;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CreatedVoucherModificationListOrderStreamReader extends
    ListOrderItemStreamReader<CreatedVoucher> {

}

