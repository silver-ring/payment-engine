package com.optiva.topup.voms.common.entities.vouchers;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

class PendingUsageVoucherTest {

  private static final Class<PendingUsageVoucher> CLAZZ_UNDER_TEST = PendingUsageVoucher.class;
  //  private static final Class<PendingUsageVoucher.PendingUsageVoucherBuilder> CLAZZ_UNDER_TEST_BUILDER =
  //      PendingUsageVoucher.PendingUsageVoucherBuilder.class;

  @Test
  void shouldHaveAccessToFields() {
    PojoClass pojo = PojoClassFactory.getPojoClass(CLAZZ_UNDER_TEST);
    ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new GetterTester())
        .build()
        .validate(pojo);
  }

  @Test
  void shouldHaveUsualMethodsWellImplemented() {
    assertPojoMethodsFor(CLAZZ_UNDER_TEST)
        .testing(Method.GETTER)
        .testing(Method.SETTER)
        .testing(Method.TO_STRING)
        .testing(Method.EQUALS)
        .testing(Method.HASH_CODE)
        .testing(Method.CONSTRUCTOR)
        .areWellImplemented();
  }

  //  @Test
  //  void builderShouldHaveUsualMethodsWellImplemented() {
  //    assertPojoMethodsFor(CLAZZ_UNDER_TEST_BUILDER)
  //        .testing(Method.TO_STRING)
  //        .areWellImplemented();
  //  }

}
