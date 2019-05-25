package com.optiva.topup.voms.rest.interceptors;

import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.LIST_ORDER_FILES_PATH;

import com.optiva.topup.voms.common.entities.file.TemporaryFile;
import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import com.optiva.topup.voms.common.entities.orders.ListOrder;
import com.optiva.topup.voms.common.messages.BatchOrderSchedulerMessage;
import com.optiva.topup.voms.common.repositories.TemporaryFileRepo;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BatchOrderScheduleInterceptor implements WithBatchOrderTopicsSupport {

  private final FileManagerConfigParameterRepo fileManagerConfigParameterRepo;
  private final FileUtil fileUtil;
  private final TemporaryFileRepo temporaryFileRepo;

  @Autowired
  public BatchOrderScheduleInterceptor(FileManagerConfigParameterRepo fileManagerConfigParameterRepo,
      FileUtil fileUtil,
      TemporaryFileRepo temporaryFileRepo) {
    this.fileManagerConfigParameterRepo = fileManagerConfigParameterRepo;
    this.fileUtil = fileUtil;
    this.temporaryFileRepo = temporaryFileRepo;
  }

  @AfterReturning(pointcut = "@annotation(batchOrderSchedule)", returning = "batchOrder")
  public void sendBatchOrderScheduleMessage(BatchOrderSchedule batchOrderSchedule, BatchOrder batchOrder)
      throws IOException {
    if (batchOrder instanceof ListOrder) {
      ListOrder listOrder = (ListOrder) batchOrder;
      moveTmpFileToOrderFolder(listOrder.getFileName());
    }
    schedulerBatchOrder(batchOrderSchedule, batchOrder);
  }

  private void moveTmpFileToOrderFolder(String fileName) throws IOException {

    TemporaryFile temporaryFile = temporaryFileRepo.findByFileName(fileName);

    String listOrderFilesPath =
        fileManagerConfigParameterRepo.findByParameter(LIST_ORDER_FILES_PATH).getValue();
    File tmpFile = fileUtil.openFile(temporaryFile.getFilePath(), temporaryFile.getFileName());
    File orderFile = fileUtil.createFile(listOrderFilesPath, fileName);
    FileUtils.copyFile(tmpFile, orderFile);
  }

  private void schedulerBatchOrder(BatchOrderSchedule batchOrderSchedule, BatchOrder batchOrder) {
    BatchOrderSchedulerMessage batchOrderSchedulerMessage = new BatchOrderSchedulerMessage();
    batchOrderSchedulerMessage.setOrderId(batchOrder.getId());
    batchOrderSchedulerMessage.setExecutionTime(batchOrder.getExecutionTime());
    batchOrderSchedulerMessage.setTagId(batchOrder.getTagId());
    batchOrderSchedulerMessage.setOrderStatus(batchOrder.getBatchJobScheduleStatus());
    batchOrderSchedulerMessage.setTaskTopic(batchOrderSchedule.value());
    batchOrderSchedulerMessage.setOrderEntity(batchOrder.getClass());
    sendTopic(BATCH_ORDER_SCHEDULER_TOPIC, batchOrderSchedulerMessage);
    sendBatchOrderScheduleTopic(batchOrderSchedulerMessage);
  }

}
