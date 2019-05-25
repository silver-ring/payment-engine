package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.batch.utils.ProductionFileUtil;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationListOrder;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import com.optiva.topup.voms.common.support.WithRemoteFileTransferSupport;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ProductionFileCreationListOrderStreamWriter implements ItemStreamWriter<ProductionVoucher>,
    WithRemoteFileTransferSupport {

  private final ProductionFileUtil productionFileUtil;
  @Value("#{jobParameters[batchOrder]}")
  private ProductionFileCreationListOrder listOrder;
  @Value("#{jobParameters[productionFileResource]}")
  private Resource productionFileResource;
  private DelimitedLineAggregator<ProductionVoucher> dla;
  private ByteArrayOutputStream content;

  @Autowired
  public ProductionFileCreationListOrderStreamWriter(ProductionFileUtil productionFileUtil) {
    this.productionFileUtil = productionFileUtil;
  }

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    dla = productionFileUtil.getProductionDelimitedLineAggregator();
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    try {
      content = new ByteArrayOutputStream();
      content.write(getHeader());
    } catch (IOException ex) {
      throw new ProductionFileListOrderWriteOpenException(ex);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    // do nothing
  }

  @Override
  public void close() throws ItemStreamException {
    try {
      byte[] encryptionKey = listOrder.getVoucherPrinter().getEncryptionKeyFileData();
      File outputFile = productionFileUtil
          .createEncryptedProductionFile(encryptionKey, content, productionFileResource.getFile());

      if (listOrder.getShouldRemoteTransfer()) {
        String remoteUrl = listOrder.getVoucherPrinter().getRemoteFileTransferUrl();
        sendRemoteFileTransferMessage(remoteUrl, outputFile);
      }

    } catch (IOException ex) {
      throw new ProductionFileListOrderWriteCloseException(ex);
    }
  }

  private byte[] getHeader() {
    return productionFileUtil.getProductionFileHeader(listOrder.getTagId(),
        listOrder.getLayoutId());
  }

  @Override
  public void write(List<? extends ProductionVoucher> items) throws Exception {
    for (ProductionVoucher productionVoucher : items) {
      String line = dla.aggregate(productionVoucher) + productionFileUtil.getEndOfLineDelimiter();
      content.write(line.getBytes(StandardCharsets.UTF_8));
    }
  }

}
