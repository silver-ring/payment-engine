package com.optiva.topup.voms.rest.interceptors;

import com.optiva.topup.voms.common.utils.VoucherCipher;
import java.util.Collection;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class VoucherIdDecryptionInterceptor {

  private final VoucherCipher voucherCipher;

  @Autowired
  public VoucherIdDecryptionInterceptor(VoucherCipher voucherCipher) {
    this.voucherCipher = voucherCipher;
  }

  @AfterReturning(value = "@annotation(voucherIdDecryption)", returning = "obj")
  public Object processVoucherIdDecryption(JoinPoint joinPoint, VoucherIdDecryption voucherIdDecryption,
      Object obj) {
    if (obj == null) {
      return obj;
    }
    if (obj instanceof Long) {
      return decryptVoucherId((Long) obj);
    } else if (obj instanceof Collection) {
      return decryptVoucherId(voucherIdDecryption, (Collection) obj);
    } else {
      return decryptVoucherId(voucherIdDecryption, obj);
    }
  }

  private Object decryptVoucherId(VoucherIdDecryption voucherIdDecryption, Collection<?> objs) {
    return objs.stream().map(item -> decryptVoucherId(voucherIdDecryption, item))
        .collect(Collectors.toList());
  }

  private Object decryptVoucherId(VoucherIdDecryption voucherIdDecryption, Object obj) {
    try {
      Long voucherId = Long.parseLong(BeanUtils.getProperty(obj, voucherIdDecryption.value()));
      Long newVoucherId = decryptVoucherId(voucherId);
      BeanUtils.setProperty(obj, voucherIdDecryption.value(), newVoucherId);
      return obj;
    } catch (Exception ex) {
      throw new ReadingVoucherIdException(ex);
    }
  }

  private Long decryptVoucherId(Long voucherId) {
    return voucherCipher.decrypt(voucherId);
  }

}
