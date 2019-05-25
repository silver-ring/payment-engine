package com.optiva.topup.voms.scheduler.triggers.useremaildomainmigration;

import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UedmUserEmailDomainMigrationRepo extends JpaRepository<UserEmailDomainMigration, Integer> {

}
