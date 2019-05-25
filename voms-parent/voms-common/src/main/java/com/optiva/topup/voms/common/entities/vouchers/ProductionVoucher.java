package com.optiva.topup.voms.common.entities.vouchers;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductionVoucher extends Voucher {

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private Integer rechargePeriod;

}
