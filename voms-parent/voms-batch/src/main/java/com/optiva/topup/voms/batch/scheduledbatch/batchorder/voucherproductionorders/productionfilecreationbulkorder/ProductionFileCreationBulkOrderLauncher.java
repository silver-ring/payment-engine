package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PRODUCTION_FILE_CREATION_BULK_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.BulkOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.batch.utils.ProductionFileUtil;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationBulkOrder;
import com.optiva.topup.voms.common.entities.vouchers.ProductionVoucher;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductionFileCreationBulkOrderLauncher extends
    BulkOrderJobLauncher<ProductionFileCreationBulkOrder> {

  private final ProductionFileCreationBulkOrderJobListener jobListener;
  private final ProductionFileCreationBulkOrderStreamWriter streamWriter;
  private final ProductionFileCreationBulkOrderErrorHandler errorHandler;
  private final ProductionFileUtil productionFileUtil;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public ProductionFileCreationBulkOrderLauncher(
      ProductionFileCreationBulkOrderJobListener jobListener,
      ProductionFileCreationBulkOrderStreamWriter streamWriter,
      ProductionFileCreationBulkOrderErrorHandler errorHandler,
      ProductionFileUtil productionFileUtil, JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamWriter = streamWriter;
    this.errorHandler = errorHandler;
    this.productionFileUtil = productionFileUtil;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = PRODUCTION_FILE_CREATION_BULK_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected JobParametersBuilder buildJobParameters(
      ProductionFileCreationBulkOrder prodFileCreationBulkOrder) {
    JobParametersBuilder jobParametersBuilder = super.buildJobParameters(prodFileCreationBulkOrder);
    productionFileUtil
        .addProductionFileSourceParameter(jobParametersBuilder,
            prodFileCreationBulkOrder.getVoucherPrinter().getName(),
            prodFileCreationBulkOrder.getTagId());
    return jobParametersBuilder;
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<ProductionFileCreationBulkOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(ProductionFileCreationBulkOrder prodFileCreationBulkOrder) {

    final String stepName = "ProductionFileCreationBulkOrderStep";
    final String readerName = "ProductionFileCreationBulkOrderReader";

    Long startSerialNumber = prodFileCreationBulkOrder.getStartSerialNumber();
    Long endSerialNumber = prodFileCreationBulkOrder.getEndSerialNumber();

    JpaPagingItemReader<ProductionVoucher> itemReader = jobBuilderUtils
        .readBySerialNumberRange(readerName, ProductionVoucher.class, startSerialNumber, endSerialNumber);

    return jobBuilderUtils.buildStep(stepName, itemReader, streamWriter);
  }

}
