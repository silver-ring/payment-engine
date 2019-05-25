package com.optiva.topup.voms.common.entities.orders;

import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
@Setter
public abstract class BulkOrder extends BatchOrder {

}
