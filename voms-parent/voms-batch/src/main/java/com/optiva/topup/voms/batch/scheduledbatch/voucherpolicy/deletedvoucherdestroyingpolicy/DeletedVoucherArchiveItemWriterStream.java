package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Log4j2
public class DeletedVoucherArchiveItemWriterStream implements ItemStreamWriter<DeletedVoucher> {

  private static final String[] VOUCHER_HISTORY_PROPERTIES_NAME = new String[]{"serialNumber",
      "expirationDate", "rechargePeriod",
      "rechargeValueId", "voucherTypeId", "voucherProviderId", "transactionId", "msisdn", "ccid",
      "finalRechargeValue", "usageRequestTime", "voucherStatus", "eventTimestamp"};
  private final DvdpVoucherHistoryRepo voucherHistoryRepo;
  private final FileUtil fileUtil;
  private DelimitedLineAggregator<VoucherHistory> dla;
  private ByteArrayOutputStream content;
  @Value("#{jobParameters[archiveFileResource]}")
  private Resource archiveFileResource;

  @Autowired
  public DeletedVoucherArchiveItemWriterStream(DvdpVoucherHistoryRepo voucherHistoryRepo, FileUtil fileUtil) {
    this.voucherHistoryRepo = voucherHistoryRepo;
    this.fileUtil = fileUtil;
  }

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    dla = new DelimitedLineAggregator<>();
    dla.setDelimiter(fileUtil.getLineDelimiter());
    BeanWrapperFieldExtractor<VoucherHistory> fieldExtractor = new BeanWrapperFieldExtractor<>();
    fieldExtractor.setNames(VOUCHER_HISTORY_PROPERTIES_NAME);
    dla.setFieldExtractor(fieldExtractor);
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    try {
      content = new ByteArrayOutputStream();
      content.write(getHeader());
    } catch (IOException ex) {
      throw new VoucherArchiveWriteOpenException(ex);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    // do nothing
  }

  @Override
  public void close() throws ItemStreamException {
    try {
      FileUtils.writeByteArrayToFile(archiveFileResource.getFile(), content.toByteArray());
    } catch (IOException e) {
      throw new VoucherArchiveWriteCloseException(e);
    }
  }

  private byte[] getHeader() {

    StringBuilder header = new StringBuilder();

    for (int i = 0; ; ) {
      String propertyName = VOUCHER_HISTORY_PROPERTIES_NAME[i];
      header.append(propertyName);

      if (++i < VOUCHER_HISTORY_PROPERTIES_NAME.length) {
        header.append(fileUtil.getHeaderDelimiter());
      } else {
        break;
      }
    }
    header.append(fileUtil.getEndOfLineDelimiter());
    return header.toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public void write(List<? extends DeletedVoucher> items) {

    int pageNumber = 0;
    int pageSize = 1000;

    List<Long> serialNumbers = items.stream().map(Voucher::getSerialNumber)
        .collect(Collectors.toList());

    Pageable firstPageable = PageRequest.of(pageNumber, pageSize);
    Page<VoucherHistory> voucherHistoriesPage = voucherHistoryRepo
        .findAllBySerialNumberInOrderBySerialNumberAscEventTimestampAsc(serialNumbers, firstPageable);

    List<VoucherHistory> firstVoucherHistories = voucherHistoriesPage.getContent();
    writeVoucherHistoryRaw(firstVoucherHistories);

    while (!voucherHistoriesPage.isLast()) {
      Pageable nextPageable = voucherHistoriesPage.nextPageable();
      voucherHistoriesPage = voucherHistoryRepo
          .findAllBySerialNumberInOrderBySerialNumberAscEventTimestampAsc(serialNumbers, nextPageable);
      List<VoucherHistory> voucherHistories = voucherHistoriesPage.getContent();
      writeVoucherHistoryRaw(voucherHistories);
    }
  }

  private void writeVoucherHistoryRaw(List<VoucherHistory> voucherHistories) {
    voucherHistories.forEach(voucherHistory -> {
      String line = dla.aggregate(voucherHistory) + fileUtil.getEndOfLineDelimiter();
      try {
        content.write(line.getBytes(StandardCharsets.UTF_8));
      } catch (IOException ex) {
        log.error(ex);
      }
    });
  }

}
