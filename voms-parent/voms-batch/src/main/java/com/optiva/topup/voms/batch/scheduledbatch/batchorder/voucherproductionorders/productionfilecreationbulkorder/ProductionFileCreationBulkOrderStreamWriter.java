package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import com.optiva.topup.voms.batch.utils.ProductionFileUtil;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationBulkOrder;
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
public class ProductionFileCreationBulkOrderStreamWriter implements ItemStreamWriter<ProductionVoucher>,
    WithRemoteFileTransferSupport {

  private final ProductionFileUtil productionFileUtil;

  @Value("#{jobParameters[batchOrder]}")
  private ProductionFileCreationBulkOrder bulkOrder;

  @Value("#{jobParameters[productionFileResource]}")
  private Resource productionFileResource;

  private DelimitedLineAggregator<ProductionVoucher> dla;
  private ByteArrayOutputStream content;

  @Autowired
  public ProductionFileCreationBulkOrderStreamWriter(ProductionFileUtil productionFileUtil) {
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
      throw new ProductionFileBulkOrderWriteOpenException(ex);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    // do nothing
  }

  @Override
  public void close() throws ItemStreamException {
    try {
      byte[] encryptionKey = bulkOrder.getVoucherPrinter().getEncryptionKeyFileData();
      File outputFile = productionFileUtil
          .createEncryptedProductionFile(encryptionKey, content, productionFileResource.getFile());

      if (bulkOrder.getShouldRemoteTransfer()) {
        String remoteUrl = bulkOrder.getVoucherPrinter().getRemoteFileTransferUrl();
        sendRemoteFileTransferMessage(remoteUrl, outputFile);
      }
    } catch (IOException ex) {
      throw new ProductionFileBulkOrderWriteCloseException(ex);
    }

  }

  private byte[] getHeader() {
    return productionFileUtil.getProductionFileHeader(bulkOrder.getTagId(),
        bulkOrder.getLayoutId());
  }

  @Override
  public void write(List<? extends ProductionVoucher> items) throws Exception {
    for (ProductionVoucher productionVoucher : items) {
      if (dla != null && content != null) {
        String line = dla.aggregate(productionVoucher) + productionFileUtil.getEndOfLineDelimiter();
        content.write(line.getBytes(StandardCharsets.UTF_8));
      }
    }
  }
}
