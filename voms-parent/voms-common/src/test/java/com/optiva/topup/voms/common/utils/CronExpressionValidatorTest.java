package com.optiva.topup.voms.common.utils;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CronExpressionValidatorTest {

  @Test
  void shouldBeTrueWithEverySecondCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("* * * * * ?"), is(true));
  }

  @Test
  void shouldBeTrueWithComplexCronExpression() {
    Assert.assertThat(
        CronExpressionValidator.isValidExpression("0/5 14,18,3-39,52 * ? JAN,MAR,SEP MON-FRI 2002-2010"),
        is(true));
  }

  @Test
  void shouldAcceptLastDayOfMonthCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("0 15 10 15,L * ?"), is(true));
  }

  @Test
  void shouldBeFalseWhenUsingLastDayOfWeekCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("0 15 10 * * 3,L"), is(false));
  }

  @Test
  void shouldBeFalseWhenUsingMultiplesDayOfWeekCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("0 15 10 ? * #6#3"), is(false));
  }

  @Test
  void shouldBeFalseWhenWrongNumbersCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("61 * * * * ?"), is(false));
    Assert.assertThat(CronExpressionValidator.isValidExpression("* 61 * * * ?"), is(false));
    Assert.assertThat(CronExpressionValidator.isValidExpression("* * 61 * * ?"), is(false));
    Assert.assertThat(CronExpressionValidator.isValidExpression("* * * 61 * ?"), is(false));
    Assert.assertThat(CronExpressionValidator.isValidExpression("* * * * 61 ?"), is(false));
    Assert.assertThat(CronExpressionValidator.isValidExpression("* * * * * 61"), is(false));
  }

  @Test
  void shouldBeFalseWhenUsingWrongYearsRanges() {
    Assert.assertThat("Start year must be less than stop year",
        CronExpressionValidator.isValidExpression("0 0 0 ? * * 2016-2010"), is(false));
  }

  @Test
  void shouldBeAllValid() {
    //All valid expressions got from here: https://www.freeformatter.com/cron-expression-generator-quartz.html
    assertAllTrue(
        "* * * ? * *",
        "0 * * ? * *",
        "0 */2 * ? * *",
        "0 1/2 * ? * *",
        "0 */2 * ? * *",
        "0 */4 * ? * *",
        "0 */5 * ? * *",
        "0 */10 * ? * *",
        "0 */15 * ? * *",
        "0 */30 * ? * *",
        "0 15,30,45 * ? * *",
        "0 0 * ? * *",
        "0 0 */2 ? * *",
        "0 0 0/2 ? * *",
        "0 0 1/2 ? * *",
        "0 0 */3 ? * *",
        "0 0 */4 ? * *",
        "0 0 */6 ? * *",
        "0 0 */8 ? * *",
        "0 0 */12 ? * *",
        "0 0 0 * * ?",
        "0 0 1 * * ?",
        "0 0 6 * * ?",
        "0 0 12 * * ?",
        "0 0 12 ? * SUN *",
        "0 0 12 ? * MON *",
        "0 0 12 ? * TUE *",
        "0 0 12 ? * WED *",
        "0 0 12 ? * THU *",
        "0 0 12 ? * FRI *",
        "0 0 12 ? * SAT *",
        "0 0 12 ? * MON-FRI *",
        "0 0 12 ? * SUN,SAT *",
        "0 0 12 1L * ?",
        "0 0 12 2L * ?",
        "0 0 12 6L * ?",
        "0 0 12 */7 * ?",
        "0 0 12 1 * ?",
        "0 0 12 2 * ?",
        "0 0 12 15 * ?",
        "0 0 12 1/2 * ?",
        "0 0 12 1/4 * ?",
        "0 0 12 L * ?",
        "0 0 12 L-2 * ?",
        "0 0 12 LW * ?",
        "0 0 12 1W * ?",
        "0 0 12 15W * ?",
        "0 0 12 ? * 2#1",
        "0 0 12 ? * 6#1",
        "0 0 12 ? * 2#2",
        "0 0 12 ? * 5#3",
        "0 0 12 ? JAN *",
        "0 0 12 ? JUN *",
        "0 0 12 ? JAN,JUN *",
        "0 0 12 ? DEC *",
        "0 0 12 ? JAN,FEB,MAR,APR *",
        "0 0 12 ? 9-12 *",
        "0 0 0 ? * * 2016-2020"
    );
  }

  private void assertAllTrue(String... expressions) {
    for (String expression : expressions) {
      Assert.assertThat(String.format("Expression %s should be valid", expression),
          CronExpressionValidator.isValidExpression(expression), is(true));
    }
  }

  @Test
  void shouldBeTrueWhenUsingJustOneMultiplesDayOfWeekCronExpression() {
    Assert.assertThat(CronExpressionValidator.isValidExpression("0 15 10 ? * 6#3"), is(true));
  }

  @Test
  void shouldBeThrowExceptionIfNull() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
        Assert.assertThat(CronExpressionValidator.isValidExpression(null), is(false)));
  }
}
