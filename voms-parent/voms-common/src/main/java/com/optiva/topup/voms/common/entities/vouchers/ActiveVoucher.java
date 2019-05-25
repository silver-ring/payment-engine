package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.listeners.ActiveVoucherListener;
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
@EntityListeners({ActiveVoucherListener.class, AuditEntityListener.class})
public class ActiveVoucher extends Voucher {

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private Integer rechargePeriod;

  @ManyToOne(optional = false)
  private RechargeValue rechargeValue;

  @ManyToOne(optional = false)
  private VoucherType voucherType;

  @ManyToOne(optional = false)
  private VoucherProvider voucherProvider;

}
