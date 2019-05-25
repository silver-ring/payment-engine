package com.optiva.topup.voms.batch.utils;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import org.springframework.batch.core.JobParameter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CustomJobParameter<T> extends JobParameter {

  private final T customParam;

  public CustomJobParameter(T customParam) {
    super(UUID.randomUUID().toString());//This is to avoid duplicate JobInstance error
    this.customParam = customParam;
  }

  public T getValue() {
    return customParam;
  }

}
