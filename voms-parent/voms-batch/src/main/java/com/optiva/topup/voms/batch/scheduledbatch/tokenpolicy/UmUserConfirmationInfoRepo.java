package com.optiva.topup.voms.batch.scheduledbatch.tokenpolicy;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UmUserConfirmationInfoRepo extends JpaRepository<UserConfirmationToken, Integer> {

}
