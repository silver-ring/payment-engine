package com.optiva.topup.voms.common.converters;

import com.optiva.topup.voms.common.utils.ApplicationContextUtil;
import com.optiva.topup.voms.common.utils.VoucherCipher;
import javax.persistence.AttributeConverter;

public class VoucherIdConverter implements AttributeConverter<Long, Long> {

  @Override
  public Long convertToDatabaseColumn(Long attributeValue) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);
    return voucherIdCipher.encrypt(attributeValue);
  }

  @Override
  public Long convertToEntityAttribute(Long dbValue) {
    VoucherCipher voucherIdCipher = ApplicationContextUtil.getApplicationContext()
        .getBean(VoucherCipher.class);
    return voucherIdCipher.decrypt(dbValue);
  }

}
