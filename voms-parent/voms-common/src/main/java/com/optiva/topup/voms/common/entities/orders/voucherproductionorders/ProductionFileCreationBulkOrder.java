package com.optiva.topup.voms.common.entities.orders.voucherproductionorders;

import com.optiva.topup.voms.common.entities.orders.BulkOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class ProductionFileCreationBulkOrder extends BulkOrder {

  @Column(nullable = false)
  private Long startSerialNumber;

  @Column(nullable = false)
  private Long endSerialNumber;

  @Column
  private String layoutId;

  @Column(nullable = false)
  private Boolean shouldRemoteTransfer;

  @ManyToOne(optional = false)
  private VoucherPrinter voucherPrinter;

}
