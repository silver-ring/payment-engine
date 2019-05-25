package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class UserEmailDomainMigrationItemProcessor implements ItemProcessor<UserAccount, UserAccount> {

  @Value("#{jobParameters[userEmailDomainMigration]}")
  private UserEmailDomainMigration userEmailDomainMigration;

  @Override
  public UserAccount process(UserAccount userAccount) throws Exception {

    String[] emailParts = userAccount.getEmail().split("@");
    String emailAddress = emailParts[0];
    String emailDomain = emailParts[1];

    String migrateFromDomain = userEmailDomainMigration.getMigrateFromDomain();
    String migrateToDomain = userEmailDomainMigration.getMigrateToDomain();

    if (StringUtils.equals(emailDomain, migrateFromDomain)) {
      userAccount.setEmail(emailAddress + "@" + migrateToDomain);
      return userAccount;
    } else {
      return null;
    }

  }

}
