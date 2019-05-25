package com.optiva.topup.voms.batch.scheduledbatch.temporaryfilecleanup;

import static com.optiva.topup.voms.common.support.WithFileTopicsSupport.TEMPORARY_FILE_CLEANUP_TOPIC;

import com.optiva.topup.voms.common.entities.file.TemporaryFile;
import com.optiva.topup.voms.common.repositories.TemporaryFileRepo;
import com.optiva.topup.voms.common.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TemporaryFileCleanup {

  private final TemporaryFileRepo temporaryFileRepo;
  private final FileUtil fileUtil;

  @Autowired
  public TemporaryFileCleanup(TemporaryFileRepo temporaryFileRepo, FileUtil fileUtil) {
    this.temporaryFileRepo = temporaryFileRepo;
    this.fileUtil = fileUtil;
  }

  @KafkaListener(topics = TEMPORARY_FILE_CLEANUP_TOPIC)
  public void deleteConfirmationTokenPolicy(Integer id) {
    TemporaryFile temporaryFile = temporaryFileRepo.findById(id).get();
    fileUtil.deleteFile(temporaryFile.getFilePath(), temporaryFile.getFileName());
    temporaryFileRepo.delete(temporaryFile);
  }

}
