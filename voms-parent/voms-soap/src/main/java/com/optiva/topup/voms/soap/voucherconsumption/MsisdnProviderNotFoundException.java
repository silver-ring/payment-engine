package com.optiva.topup.voms.soap.voucherconsumption;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class MsisdnProviderNotFoundException extends VomsException {

  public MsisdnProviderNotFoundException() {
    super("MSISDN provider ID Not Found Exception");
  }

}
