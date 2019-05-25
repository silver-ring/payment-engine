package com.optiva.topup.voms.common.entities.orders.vouchermodificationorders;

import com.optiva.topup.voms.common.entities.orders.ListOrder;
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
public class BlockedVoucherModificationListOrder extends ListOrder {

  @Column
  private LocalDate expirationDate;

  @Column
  private Integer rechargePeriod;

  @ManyToOne
  private RechargeValue rechargeValue;

  @ManyToOne
  private VoucherType voucherType;

}
