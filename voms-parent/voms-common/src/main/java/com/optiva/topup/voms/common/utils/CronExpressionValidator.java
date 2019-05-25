package com.optiva.topup.voms.common.utils;

import static com.cronutils.model.CronType.QUARTZ;
import static com.cronutils.model.field.CronFieldName.YEAR;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.field.expression.Between;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.value.FieldValue;
import com.cronutils.model.field.value.IntegerFieldValue;
import com.cronutils.parser.CronParser;
import java.io.Serializable;
import java.util.Locale;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class CronExpressionValidator implements Serializable {

  private static final long serialVersionUID = 12423409423L;

  /**
   * Indicates whether the specified cron expression can be parsed into a valid cron expression.
   *
   * @param cronExpression the expression to evaluate
   * @return a boolean indicating whether the given expression is a valid cron expression
   */
  public static boolean isValidExpression(String cronExpression) {

    if (cronExpression == null) {
      throw new IllegalArgumentException("cronExpression cannot be null");
    }

    try {
      CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
      CronParser parser = new CronParser(cronDefinition);
      Cron cron = parser.parse(cronExpression);
      cron.validate();

      if (log.isDebugEnabled()) {
        String describe = CronDescriptor.instance(Locale.UK).describe(cron);
        log.debug(describe);
      }

      if (isNotAValidYearRange(cron)) {
        return false;
      }
    } catch (IllegalArgumentException exception) {
      log.debug(exception);
      return false;
    }
    return true;
  }

  private static boolean isNotAValidYearRange(Cron cron) {
    CronField cronField = cron.retrieve(YEAR);

    if (cronField != null) {
      FieldExpression expression = cronField.getExpression();

      Between between = expression instanceof Between ? ((Between) expression) : null;

      if (between != null) {
        return toInt(between.getFrom()) > toInt(between.getTo());
      }
    }
    return false;
  }

  private static int toInt(final FieldValue<?> fieldValue) {
    if (fieldValue instanceof IntegerFieldValue) {
      return ((IntegerFieldValue) fieldValue).getValue();
    }
    throw new IllegalArgumentException("Non integer values at intervals are not fully supported yet.");
  }
}
