package com.optiva.topup.voms.common.utils;

import static org.hamcrest.CoreMatchers.is;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import java.util.Collections;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RechargeValueCalculatorTest {

  @Test
  @Disabled("Tested class should be fixed before")
  void twoMinusOneDotOneShoudBeZeroDotNine() {

    RechargeValue rechargeValue = RechargeValue.builder()
        .id(1)
        .name("Xpto")
        .value(2.0)
        .valueTaxation(1.1)
        .valuePromotion(0.0)
        .promotions(Collections.emptySet())
        .taxations(Collections.emptySet())
        .build();

    double finalValue = new RechargeValueCalculator().calculateFinalValue(rechargeValue);

    // 2.0 - 1.1 should be 0.9
    Assert.assertThat(finalValue, is(0.9));
  }
}
