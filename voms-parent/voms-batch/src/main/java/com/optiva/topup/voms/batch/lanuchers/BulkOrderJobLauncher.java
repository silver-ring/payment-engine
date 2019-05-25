package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.common.entities.orders.BulkOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BulkOrderJobLauncher<T extends BulkOrder> extends BatchOrderJobLauncher<T> {

}
