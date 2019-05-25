package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.PendingUsageVoucherListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EntityListeners({PendingUsageVoucherListener.class, AuditEntityListener.class})
@EqualsAndHashCode(callSuper = true)
@ToString
public class PendingUsageVoucher extends Voucher {

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private Integer rechargePeriod;

  @Column(nullable = false)
  private String transactionId;

  @Column(nullable = false)
  private String msisdn;

  @Column(nullable = false)
  private String ccid;

  @Column(nullable = false)
  private Double finalRechargeValue;

  @Column(nullable = false)
  private LocalDateTime usageRequestTime;

  @ManyToOne(optional = false)
  private RechargeValue rechargeValue;

  @ManyToOne(optional = false)
  private VoucherType voucherType;

  @ManyToOne(optional = false)
  private VoucherProvider voucherProvider;

}
