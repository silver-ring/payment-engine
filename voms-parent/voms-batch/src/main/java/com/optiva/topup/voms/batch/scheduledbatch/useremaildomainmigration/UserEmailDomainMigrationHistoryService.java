package com.optiva.topup.voms.batch.scheduledbatch.useremaildomainmigration;

import static com.optiva.topup.voms.common.types.BatchJobHistoryStatus.ERROR;

import com.optiva.topup.voms.common.document.UserEmailDomainMigrationHistory;
import com.optiva.topup.voms.common.entities.usermanager.UserEmailDomainMigration;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.common.UUIDs;
import org.springframework.stereotype.Service;

@Service
public class UserEmailDomainMigrationHistoryService implements WithDocumentTopicsSupport {

  public void saveProgress(UserEmailDomainMigration userEmailDomainMigration,
      BatchJobHistoryStatus batchJobHistoryStatus) {
    UserEmailDomainMigrationHistory historyDocument =
        buildUserEmailDomainMigrationHistory(userEmailDomainMigration);
    historyDocument.setBatchJobHistoryStatus(batchJobHistoryStatus);
    saveDocument(historyDocument);
  }

  public void saveError(UserEmailDomainMigration userEmailDomainMigration, Exception exception) {
    UserEmailDomainMigrationHistory historyDocument =
        buildUserEmailDomainMigrationHistory(userEmailDomainMigration);
    historyDocument.setBatchJobHistoryStatus(ERROR);
    List<String> errorMessages = new ArrayList<>();
    errorMessages.add(exception.getMessage());
    historyDocument.setErrorMessages(errorMessages);
    saveDocument(historyDocument);
  }

  private UserEmailDomainMigrationHistory buildUserEmailDomainMigrationHistory(
      UserEmailDomainMigration userEmailDomainMigration) {
    UserEmailDomainMigrationHistory historyDocument = new UserEmailDomainMigrationHistory();
    historyDocument.setId(UUIDs.randomBase64UUID());
    historyDocument.setMigrateFromDomain(userEmailDomainMigration.getMigrateFromDomain());
    historyDocument.setMigrateToDomain(userEmailDomainMigration.getMigrateToDomain());
    historyDocument.setTagId(userEmailDomainMigration.getTagId());
    historyDocument.setEventTimestamp(LocalDateTime.now());
    historyDocument.setExecutionTime(userEmailDomainMigration.getExecutionTime());
    return historyDocument;
  }

  private void saveDocument(UserEmailDomainMigrationHistory historyDocument) {
    sendDocumentMessage(historyDocument);
  }

}
