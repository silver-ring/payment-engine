package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.BlockedVoucherListener;
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
@EntityListeners({BlockedVoucherListener.class, AuditEntityListener.class})
public class BlockedVoucher extends Voucher {

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
