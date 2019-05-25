package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.batch.jobitemssupport.ListOrderItemStreamReader;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ProductionFileCreationListOrderStreamReader extends
    ListOrderItemStreamReader<ProductionVoucher> {

}
