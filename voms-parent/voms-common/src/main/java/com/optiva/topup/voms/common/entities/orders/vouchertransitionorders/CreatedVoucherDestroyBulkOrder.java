package com.optiva.topup.voms.common.entities.orders.vouchertransitionorders;

import com.optiva.topup.voms.common.entities.orders.BulkOrder;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class CreatedVoucherDestroyBulkOrder extends BulkOrder {

  @Column(nullable = false)
  private Long startSerialNumber;

  @Column(nullable = false)
  private Long endSerialNumber;

}
