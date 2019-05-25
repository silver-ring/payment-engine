package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class VoucherIdEncryptionInterceptor {

  private final VoucherCipher voucherCipher;

  @Autowired
  public VoucherIdEncryptionInterceptor(VoucherCipher voucherCipher) {
    this.voucherCipher = voucherCipher;
  }

  @Around(value = "@annotation(voucherIdEncryption) && args(obj)",
      argNames = "proceedingJoinPoint,voucherIdEncryption,obj")
  public Object processVoucherIdEncryption(ProceedingJoinPoint proceedingJoinPoint,
      VoucherIdEncryption voucherIdEncryption, Object obj) {
    if (obj == null) {
      return obj;
    }
    Object[] params;
    if (obj instanceof Long) {
      params = new Object[]{encryptVoucherId((Long) obj)};
    } else if (obj instanceof Collection) {
      params = new Object[]{encryptVoucherId(voucherIdEncryption, (Collection) obj)};
    } else {
      params = new Object[]{encryptVoucherId(voucherIdEncryption, obj)};
    }
    try {
      return proceedingJoinPoint.proceed(params);
    } catch (Throwable throwable) {
      throw new EncryptVoucherIdException(throwable);
    }

  }

  private Object encryptVoucherId(VoucherIdEncryption voucherIdEncryption, Collection<?> objs) {
    return objs.stream().map(item -> encryptVoucherId(voucherIdEncryption, item))
        .collect(Collectors.toList());
  }

  private Object encryptVoucherId(VoucherIdEncryption voucherIdEncryption, Object obj) {
    try {
      Long voucherId = Long.parseLong(BeanUtils.getProperty(obj, voucherIdEncryption.value()));
      Long newVoucherId = encryptVoucherId(voucherId);
      BeanUtils.setProperty(obj, voucherIdEncryption.value(), newVoucherId);
      return obj;
    } catch (IllegalAccessException | InvocationTargetException | NumberFormatException
        | NoSuchMethodException exception) {
      throw new ReadingVoucherIdException(exception);
    }
  }

  private Long encryptVoucherId(Long voucherId) {
    return voucherCipher.encrypt(voucherId);
  }

}
