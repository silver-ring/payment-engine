package com.optiva.topup.voms.common.entities.orders.voucherproductionorders;

import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import java.time.LocalDate;
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
public class VoucherCreationBatchOrder extends BatchOrder {

  @Column(nullable = false)
  private Integer numberOfVouchers;

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private Integer rechargePeriod;

  @ManyToOne(optional = false)
  private RechargeValue rechargeValue;

  @ManyToOne(optional = false)
  private VoucherType voucherType;

}
