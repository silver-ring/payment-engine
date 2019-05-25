package com.optiva.topup.voms.common.converters;

import com.optiva.topup.voms.common.types.UserConfirmationTokenParameterType;
import java.util.HashMap;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;

public class AdditionalInfoConverter implements
    AttributeConverter<HashMap<UserConfirmationTokenParameterType, Object>, Byte[]> {

  @Override
  public Byte[] convertToDatabaseColumn(HashMap<UserConfirmationTokenParameterType, Object> stringObjectMap) {
    return ArrayUtils.toObject(SerializationUtils.serialize(stringObjectMap));
  }

  @Override
  public HashMap<UserConfirmationTokenParameterType, Object> convertToEntityAttribute(Byte[] bytes) {
    return SerializationUtils.deserialize(ArrayUtils.toPrimitive(bytes));
  }

}
