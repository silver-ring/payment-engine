package com.optiva.topup.voms.rest.resources.voucheradmin.createdvoucheradmin;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
class CreatedVoucherDetails extends RepresentationModel {

  private VoucherType voucherType;
  private RechargeValue rechargeValue;

}
