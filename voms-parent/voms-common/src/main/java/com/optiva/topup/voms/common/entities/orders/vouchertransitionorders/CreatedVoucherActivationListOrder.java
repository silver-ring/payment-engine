package com.optiva.topup.voms.common.entities.orders.vouchertransitionorders;

import com.optiva.topup.voms.common.entities.orders.ListOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
public class CreatedVoucherActivationListOrder extends ListOrder {

  @ManyToOne
  private VoucherProvider voucherProvider;

}
