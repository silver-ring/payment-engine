package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherProvider;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
class VoucherHistoryDetails extends RepresentationModel {

  private VoucherType voucherType;
  private VoucherProvider voucherProvider;
  private RechargeValue rechargeValue;

}
