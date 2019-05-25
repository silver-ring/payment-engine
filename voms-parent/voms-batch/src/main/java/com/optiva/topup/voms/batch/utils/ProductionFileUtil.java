package com.optiva.topup.voms.batch.utils;

import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.PRODUCTION_FILES_PATH;

import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductionFileUtil {

  private final FileManagerConfigParameterRepo fileManagerConfigParameterRepo;
  private final FileUtil fileUtil;

  @Autowired
  public ProductionFileUtil(FileManagerConfigParameterRepo fileManagerConfigParameterRepo,
      FileUtil fileUtil) {
    this.fileManagerConfigParameterRepo = fileManagerConfigParameterRepo;
    this.fileUtil = fileUtil;
  }

  public void addProductionFileSourceParameter(JobParametersBuilder jobParametersBuilder,
      String voucherPrinterName, String tagId) {

    final String resourceParamName = "productionFileResource";

    Resource productionFileResource = buildProductionFileResource(voucherPrinterName, tagId);
    CustomJobParameter<Resource> resourceParam =
        new CustomJobParameter<>(productionFileResource);
    jobParametersBuilder.addParameter(resourceParamName, resourceParam);

  }

  public Resource buildProductionFileResource(String voucherPrinterName, String tagId) {

    final String productionFileExtension = "prod";
    final String fileNameDateTimeFormat = "yyyyMMddHHmmss";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(fileNameDateTimeFormat);
    String formattedDate = LocalDateTime.now().format(dateTimeFormatter);
    String prodFileName = tagId.replace(" ", "_") + "_" + formattedDate + "." + productionFileExtension;

    String productionFilesPath = fileManagerConfigParameterRepo.findByParameter(PRODUCTION_FILES_PATH)
        .getValue() + File.separator + voucherPrinterName + File.separator;

    File productionFile = fileUtil.createFile(productionFilesPath, prodFileName);

    return new FileSystemResource(productionFile);
  }

  public DelimitedLineAggregator<ProductionVoucher> getProductionDelimitedLineAggregator() {
    DelimitedLineAggregator<ProductionVoucher> dla = new DelimitedLineAggregator<>();
    dla.setDelimiter(fileUtil.getLineDelimiter());
    dla.setFieldExtractor(getProductionFileExtractor());
    return dla;

  }

  public BeanWrapperFieldExtractor<ProductionVoucher> getProductionFileExtractor() {
    final String voucherIdPropertyName = "voucherId";
    final String serialNumberPropertyName = "serialNumber";
    final String expirationDatePropertyName = "expirationDate";
    final String rechargePeriodPropertyName = "rechargePeriod";

    BeanWrapperFieldExtractor<ProductionVoucher> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(new String[]{voucherIdPropertyName, serialNumberPropertyName,
        expirationDatePropertyName, rechargePeriodPropertyName});

    return fieldExtractor;
  }

  public byte[] getProductionFileHeader(String tagId, String layoutId) {
    StringBuilder header = new StringBuilder()
        .append(tagId)
        .append(fileUtil.getHeaderDelimiter())
        .append(layoutId)
        .append(fileUtil.getEndOfLineDelimiter());
    return header.toString().getBytes(StandardCharsets.UTF_8);
  }

  public File createEncryptedProductionFile(byte[] encryptionKey, ByteArrayOutputStream content,
      File outputFile) {
    return fileUtil.createEncryptedFile(encryptionKey, content, outputFile);
  }

  public String getEndOfLineDelimiter() {
    return fileUtil.getEndOfLineDelimiter();
  }

}
