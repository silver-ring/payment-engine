package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PRODUCTION_FILE_CREATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.batch.lanuchers.ListOrderJobLauncher;
import com.optiva.topup.voms.batch.listeners.BatchOrderJobListener;
import com.optiva.topup.voms.batch.utils.BatchOrderErrorHandler;
import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.batch.utils.ProductionFileUtil;
import com.optiva.topup.voms.common.entities.orders.voucherproductionorders.ProductionFileCreationListOrder;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductionFileCreationListOrderLauncher extends
    ListOrderJobLauncher<ProductionFileCreationListOrder> {

  private final ProductionFileCreationListOrderJobListener jobListener;
  private final ProductionFileCreationListOrderStreamReader streamReader;
  private final ProductionFileCreationListOrderStreamWriter streamWriter;
  private final ProductionFileCreationListOrderErrorHandler errorHandler;
  private final ProductionFileUtil productionFileUtil;
  private final JobBuilderUtils jobBuilderUtils;

  @Autowired
  public ProductionFileCreationListOrderLauncher(
      ProductionFileCreationListOrderJobListener jobListener,
      ProductionFileCreationListOrderStreamReader streamReader,
      ProductionFileCreationListOrderStreamWriter streamWriter,
      ProductionFileCreationListOrderErrorHandler errorHandler,
      ProductionFileUtil productionFileUtil, JobBuilderUtils jobBuilderUtils) {
    this.jobListener = jobListener;
    this.streamReader = streamReader;
    this.streamWriter = streamWriter;
    this.errorHandler = errorHandler;
    this.productionFileUtil = productionFileUtil;
    this.jobBuilderUtils = jobBuilderUtils;
  }

  @KafkaListener(topics = PRODUCTION_FILE_CREATION_LIST_ORDER_TASK_TOPIC)
  @Override
  public void onMessage(Integer orderId) {
    launch(orderId);
  }

  @Override
  protected BatchOrderJobListener getJobListener() {
    return jobListener;
  }

  @Override
  protected BatchOrderErrorHandler<ProductionFileCreationListOrder> getErrorHandler() {
    return errorHandler;
  }

  @Override
  protected Step step(ProductionFileCreationListOrder batchOrder) {

    final String stepName = "ProductionFileCreationListOrderStep";

    return jobBuilderUtils.buildStep(stepName, streamReader,
        streamWriter);

  }

  @Override
  protected JobParametersBuilder buildJobParameters(
      ProductionFileCreationListOrder prodFileCreationListOrder) {
    JobParametersBuilder jobParametersBuilder = super.buildJobParameters(prodFileCreationListOrder);
    productionFileUtil
        .addProductionFileSourceParameter(jobParametersBuilder,
            prodFileCreationListOrder.getVoucherPrinter().getName(),
            prodFileCreationListOrder.getTagId());
    return jobParametersBuilder;
  }


}
