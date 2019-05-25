package com.optiva.topup.voms.common.entities.voucherconfig;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Currency {

  @Column(nullable = false)
  private Integer base;
  @Column(nullable = false)
  private String unit;

}
