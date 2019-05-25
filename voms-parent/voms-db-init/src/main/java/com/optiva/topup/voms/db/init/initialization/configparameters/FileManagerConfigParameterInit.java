package com.optiva.topup.voms.db.init.initialization.configparameters;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.NUMBER;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.PATH;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.ARCHIVE_FILES_PATH;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.LIST_ORDER_FILES_PATH;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.PRODUCTION_FILES_PATH;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.TEMPORARY_FILES_PATH;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.TEMPORARY_FILE_KEEP_DURATION;

import com.optiva.topup.voms.common.entities.configparameters.FileManagerConfigParameter;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileManagerConfigParameterInit implements DataInitializer {

  private final FileManagerConfigParameterRepo fileManagerConfigParameterRepo;

  @Autowired
  public FileManagerConfigParameterInit(FileManagerConfigParameterRepo fileManagerConfigParameterRepo) {
    this.fileManagerConfigParameterRepo = fileManagerConfigParameterRepo;
  }

  public void init() {
    List<FileManagerConfigParameter> voucherConfigParameters = new ArrayList<>();
    voucherConfigParameters.add(getProductionFilesPathParameter());
    voucherConfigParameters.add(getTemporaryFilesPathParameter());
    voucherConfigParameters.add(getTemporaryFileKeepDurationParameter());
    voucherConfigParameters.add(getListOrderFilesPathParameter());
    voucherConfigParameters.add(getArchiveFilesPathParameter());
    fileManagerConfigParameterRepo.saveAll(voucherConfigParameters);
  }

  private FileManagerConfigParameter getArchiveFilesPathParameter() {
    FileManagerConfigParameter configParameter = new FileManagerConfigParameter();
    configParameter.setParameter(ARCHIVE_FILES_PATH);
    configParameter.setDescription("Folder path which voucher archive files will be located");
    configParameter.setDefaultValue("/voms/data/voucher-archive/");
    configParameter.setValue("/voms/data/voucher-archive/");
    configParameter.setValueType(PATH);
    configParameter.setOptional(false);
    return configParameter;
  }

  private FileManagerConfigParameter getListOrderFilesPathParameter() {
    FileManagerConfigParameter configParameter = new FileManagerConfigParameter();
    configParameter.setParameter(LIST_ORDER_FILES_PATH);
    configParameter.setDescription("Folder path which list orders files will be located");
    configParameter.setDefaultValue("/voms/data/list-orders-files/");
    configParameter.setValue("/voms/data/list-orders-files/");
    configParameter.setValueType(PATH);
    configParameter.setOptional(false);
    return configParameter;
  }

  private FileManagerConfigParameter getTemporaryFileKeepDurationParameter() {
    FileManagerConfigParameter configParameter = new FileManagerConfigParameter();
    configParameter.setParameter(TEMPORARY_FILE_KEEP_DURATION);
    configParameter
        .setDescription("Duration in minuets to keep temporary file before cleanup ");
    configParameter.setDefaultValue("30");
    configParameter.setValue("30");
    configParameter.setValueType(NUMBER);
    configParameter.setOptional(false);
    return configParameter;
  }

  private FileManagerConfigParameter getTemporaryFilesPathParameter() {
    FileManagerConfigParameter configParameter = new FileManagerConfigParameter();
    configParameter.setParameter(TEMPORARY_FILES_PATH);
    configParameter.setDescription("Folder path which list orders files will be located");
    configParameter.setDefaultValue("/voms/data/tmp/");
    configParameter.setValue("/voms/data/tmp/");
    configParameter.setValueType(PATH);
    configParameter.setOptional(false);
    return configParameter;
  }

  private FileManagerConfigParameter getProductionFilesPathParameter() {
    FileManagerConfigParameter configParameter = new FileManagerConfigParameter();
    configParameter.setParameter(PRODUCTION_FILES_PATH);
    configParameter
        .setDescription("Folder path which the generated production files will be located");
    configParameter.setDefaultValue("/voms/data/production-files/");
    configParameter.setValue("/voms/data/production-files/");
    configParameter.setValueType(PATH);
    configParameter.setOptional(false);
    return configParameter;
  }

}
