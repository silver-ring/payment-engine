package com.optiva.topup.voms.rest.resources.usermanager.useremaildomainmigration;

import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import com.optiva.topup.voms.common.messages.UserEmailDomainMigrationMessage;
import com.optiva.topup.voms.common.support.WithUserEmailDomainMigrationTopicsSupport;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UserEmailDomainMigrationEventHandler implements WithUserEmailDomainMigrationTopicsSupport {

  @HandleAfterCreate
  public void handleAfterCreate(UserEmailDomainMigration userEmailDomainMigration) {
    sendScheduleTopic(userEmailDomainMigration);
  }

  @HandleAfterSave
  public void handleAfterSave(UserEmailDomainMigration userEmailDomainMigration) {
    sendScheduleTopic(userEmailDomainMigration);
  }

  private void sendScheduleTopic(UserEmailDomainMigration userEmailDomainMigration) {
    UserEmailDomainMigrationMessage message = new UserEmailDomainMigrationMessage();
    message.setBatchJobId(userEmailDomainMigration.getId());
    message
        .setBatchJobScheduleStatus(userEmailDomainMigration.getBatchJobScheduleStatus());
    message.setExecutionTime(userEmailDomainMigration.getExecutionTime());
    sendUserEmailDomainMigrationScheduleTopic(message);
  }

}
