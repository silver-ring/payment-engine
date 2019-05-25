package com.optiva.topup.voms.rest.resources.file;

import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.TEMPORARY_FILES_PATH;

import com.optiva.topup.voms.common.entities.file.TemporaryFile;
import com.optiva.topup.voms.common.repositories.TemporaryFileRepo;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.common.support.WithFileTopicsSupport;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@BasePathAwareController
@RestController
@PreAuthorize("isAuthenticated()")
public class TemporaryFileResource implements WithFileTopicsSupport {

  @Autowired
  private FileManagerConfigParameterRepo fileManagerConfigParameterRepo;

  @Autowired
  private FileUtil fileUtil;

  @Autowired
  private TemporaryFileRepo temporaryFileRepo;

  @PostMapping(path = "/temporaryFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> post(@RequestPart MultipartFile file) throws IOException {

    if (file == null) {
      throw new UploadFileNotSelectedException();
    }

    String filesPath = fileManagerConfigParameterRepo.findByParameter(TEMPORARY_FILES_PATH).getValue();

    String fileName = UUID.randomUUID().toString();
    File createdFile = fileUtil.createFile(filesPath, fileName);
    fileUtil.writeFile(createdFile, file.getBytes());

    TemporaryFile temporaryFile = new TemporaryFile();
    temporaryFile.setFileName(fileName);
    temporaryFile.setFilePath(filesPath);

    TemporaryFile newTemporaryFile = temporaryFileRepo.save(temporaryFile);

    sendTemporaryFileCleanupScheduleTopic(newTemporaryFile.getId());

    return new ResponseEntity<>(fileName, HttpStatus.OK);
  }

}
