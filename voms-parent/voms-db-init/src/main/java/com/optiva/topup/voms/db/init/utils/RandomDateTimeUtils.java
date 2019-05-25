package com.optiva.topup.voms.db.init.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomDateTimeUtils {

  private static LocalDate futureDate() {
    return LocalDate.now().plusYears(RandomUtils.nextLong(1, 5)).plusMonths(RandomUtils.nextLong(1, 12))
        .plusDays(RandomUtils.nextLong(1, 28));
  }

  private static LocalDate pastDate() {
    return LocalDate.now().minusYears(RandomUtils.nextLong(1, 5)).minusMonths(RandomUtils.nextLong(1, 12))
        .minusDays(RandomUtils.nextLong(1, 28));
  }

  public static LocalDateTime futureTime() {
    return LocalDateTime.now().plusHours(RandomUtils.nextLong(1, 24))
        .plusMinutes(RandomUtils.nextLong(1, 60));
  }

  public static LocalDateTime pastTime() {
    return LocalDateTime.now().minusHours(RandomUtils.nextLong(1, 24))
        .plusMinutes(RandomUtils.nextLong(1, 60));
  }


  public static LocalDate futureOrPastDate() {
    if (RandomUtils.nextBoolean()) {
      return futureDate();
    } else {
      return pastDate();
    }
  }

}
