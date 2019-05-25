package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class MissingRemoteServerInformation extends VomsException {

  public MissingRemoteServerInformation() {
    super("Remote server information is missing");
  }

}
