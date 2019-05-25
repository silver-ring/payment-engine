package com.optiva.topup.voms.rest.resources.dashboard.activerechargechart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveRechargeChartDto {

  private String day;
  private Integer activeVouchers;
  private Integer recharges;

}
