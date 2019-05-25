package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.CreatedVoucherListener;
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
@EntityListeners({CreatedVoucherListener.class, AuditEntityListener.class})
public class CreatedVoucher extends Voucher {

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private Integer rechargePeriod;

  @ManyToOne
  private RechargeValue rechargeValue;

  @ManyToOne
  private VoucherType voucherType;

}
