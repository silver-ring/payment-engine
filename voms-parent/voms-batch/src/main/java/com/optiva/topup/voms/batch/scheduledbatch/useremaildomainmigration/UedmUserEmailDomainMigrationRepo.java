package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UedmUserEmailDomainMigrationRepo extends JpaRepository<UserEmailDomainMigration, Integer> {

}
