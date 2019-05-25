package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.UsedVoucherListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({UsedVoucherListener.class, AuditEntityListener.class})
public class UsedVoucher extends Voucher {

  @Column(nullable = false)
  private Double finalRechargeValue;

  @Column(nullable = false)
  private String msisdn;

  @Column(nullable = false)
  private String ccid;

}
