package com.optiva.topup.voms.common.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public enum ConfigParameterValueType {
  NUMBER,
  PATH,
  STRING,
  LIST,
  MAP;

  public static Map<String, String> getValueAsMap(String value) {
    Map<String, String> result = new HashMap<>();
    if (StringUtils.isEmpty(value)) {
      return result;
    }
    String[] elements = value.split(",");
    for (String element : elements) {
      String[] propertyMap = element.split("=");
      result.put(propertyMap[0], propertyMap[1]);
    }
    return result;
  }

  public static List<String> getValueAsList(String value) {
    if (StringUtils.isEmpty(value)) {
      return new ArrayList<>();
    }
    return Arrays.asList(value.split(","));
  }

}
