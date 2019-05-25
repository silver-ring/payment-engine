package com.optiva.topup.voms.db.init.initialization.usermanager;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class GuiUserAuthorityInitException extends VomsException {

  public GuiUserAuthorityInitException(Exception ex) {
    super("Gui User Authority Init Exception", ex);
  }

}
