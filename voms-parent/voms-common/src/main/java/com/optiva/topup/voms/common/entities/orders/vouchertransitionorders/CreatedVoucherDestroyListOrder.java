package com.optiva.topup.voms.common.entities.orders.vouchertransitionorders;

import com.optiva.topup.voms.common.entities.orders.ListOrder;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners(AuditEntityListener.class)
public class CreatedVoucherDestroyListOrder extends ListOrder {

}
