package com.optiva.topup.voms.batch.listeners;

import com.optiva.topup.voms.common.entities.orders.BulkOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BulkOrderJobListener<T extends BulkOrder> extends BatchOrderJobListener<T> {

}
