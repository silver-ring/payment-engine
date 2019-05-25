package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class UserEmailDomainMigrationLauncherException extends VomsException {

  public UserEmailDomainMigrationLauncherException(Exception ex) {
    super("User Email Domain Migration Launcher Error", ex);
  }

}
