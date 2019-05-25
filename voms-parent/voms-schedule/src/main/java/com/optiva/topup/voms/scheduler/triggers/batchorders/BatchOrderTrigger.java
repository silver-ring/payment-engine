package com.optiva.topup.voms.scheduler.triggers.batchorders;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BATCH_ORDER_SCHEDULER_TOPIC;

import com.optiva.topup.voms.common.messages.BatchOrderSchedulerMessage;
import com.optiva.topup.voms.common.types.BatchJobScheduleStatus;
import com.optiva.topup.voms.scheduler.batchjob.WithScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BatchOrderTrigger implements WithScheduleBuilder {

  private final Scheduler scheduler;
  private final BatchOrderDao batchOrderDao;

  @Autowired
  public BatchOrderTrigger(Scheduler scheduler, BatchOrderDao batchOrderDao) {
    this.scheduler = scheduler;
    this.batchOrderDao = batchOrderDao;
  }

  @KafkaListener(topics = BATCH_ORDER_SCHEDULER_TOPIC)
  public void buildJobTrigger(BatchOrderSchedulerMessage orderMessage) throws SchedulerException {

    JobDetail orderJobDetail = buildJobDetail(orderMessage.getOrderId(), orderMessage.getTaskTopic());

    TriggerKey triggerKey =
        TriggerKey.triggerKey(orderMessage.getOrderId().toString(), orderMessage.getOrderEntity().getName());

    Trigger batchOrderTrigger = buildTrigger(triggerKey, orderJobDetail, orderMessage.getExecutionTime());

    BatchJobScheduleStatus orderStatus = orderMessage.getOrderStatus();
    switch (orderStatus) {
      case SCHEDULED:
        if (scheduler.checkExists(batchOrderTrigger.getJobKey())) {
          scheduler.deleteJob(batchOrderTrigger.getJobKey());
        }
        scheduler.scheduleJob(orderJobDetail, batchOrderTrigger);
        break;
      case PAUSED:
        scheduler.pauseTrigger(batchOrderTrigger.getKey());
        break;
      case CANCELED:
        scheduler.deleteJob(batchOrderTrigger.getJobKey());
        batchOrderDao.deleteOrder(orderMessage);
        break;
      default:
        // do nothing otherwise
    }
  }

}
