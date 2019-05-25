package com.optiva.topup.voms.rest.resources.usermanager.userregistration;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class DomainNotAllowedException extends VomsException {

  public DomainNotAllowedException() {
    super("Domain Not Allowed");
  }

}
