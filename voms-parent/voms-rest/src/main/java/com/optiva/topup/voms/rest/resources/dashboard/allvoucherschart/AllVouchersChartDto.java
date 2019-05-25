package com.optiva.topup.voms.rest.resources.dashboard.allvoucherschart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllVouchersChartDto {

  private String day;
  private Integer created;
  private Integer active;
  private Integer blocked;
  private Integer pendingUsage;
  private Integer used;
  private Integer deleted;
  private Integer archived;

}
