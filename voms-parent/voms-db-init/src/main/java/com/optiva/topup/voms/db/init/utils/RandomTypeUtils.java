package com.optiva.topup.voms.db.init.utils;

import org.apache.commons.lang3.RandomUtils;

public class RandomTypeUtils<E extends Enum> {

  private final E[] es;
  private final int enumCount;

  public RandomTypeUtils(E... es) {
    this.es = es.clone();
    enumCount = es.length;
  }

  public E randomEnums() {
    int randomIndex = RandomUtils.nextInt(0, enumCount);
    return es[randomIndex];
  }

}
