package com.optiva.topup.voms.scheduler.triggers.voucherpolicies;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport;
import com.optiva.topup.voms.common.types.VoucherPolicyType;
import com.optiva.topup.voms.common.utils.VoucherPolicyTopicUtil;
import com.optiva.topup.voms.scheduler.batchjob.WithScheduleBuilder;
import java.util.List;
import javax.annotation.PostConstruct;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class VoucherPolicyTrigger implements WithVoucherPolicyTopicsSupport, WithScheduleBuilder {

  private final Scheduler scheduler;
  private final VpVoucherPolicyScheduleRepo voucherPolicyScheduleRepo;
  private final VoucherPolicyTopicUtil voucherPolicyTopicUtil;

  @Autowired
  public VoucherPolicyTrigger(Scheduler scheduler, VpVoucherPolicyScheduleRepo voucherPolicyScheduleRepo,
      VoucherPolicyTopicUtil voucherPolicyTopicUtil) {
    this.scheduler = scheduler;
    this.voucherPolicyScheduleRepo = voucherPolicyScheduleRepo;
    this.voucherPolicyTopicUtil = voucherPolicyTopicUtil;
  }

  @PostConstruct
  public void initVoucherPolicySchedule() {
    List<VoucherPolicySchedule> voucherPolicySchedules = voucherPolicyScheduleRepo.findAll();
    voucherPolicySchedules.forEach(voucherPolicySchedule -> {
      processJobScheduling(voucherPolicySchedule, false);
    });
  }

  @KafkaListener(topics = VOUCHER_POLICY_RESCHEDULE_TOPIC)
  public void rescheduleVoucherPolicy(Integer voucherPolicyId) {
    VoucherPolicySchedule voucherPolicySchedule = voucherPolicyScheduleRepo.findById(voucherPolicyId).get();
    processJobScheduling(voucherPolicySchedule, true);
  }

  private void processJobScheduling(VoucherPolicySchedule voucherPolicySchedule, Boolean reschedule) {

    VoucherPolicyType voucherPolicyType = voucherPolicySchedule.getVoucherPolicyType();
    String voucherPolicyTopic = voucherPolicyTopicUtil.getVoucherPolicyTopic(voucherPolicyType);
    JobDetail voucherPolicyJobDetail = buildJobDetail(voucherPolicySchedule.getId(), voucherPolicyTopic);

    Trigger voucherPolicyTrigger = getVoucherPolicyTrigger(voucherPolicySchedule, voucherPolicyJobDetail);

    if (reschedule) {
      rescheduleJob(voucherPolicyTrigger);
    } else {
      scheduleJob(voucherPolicyTrigger, voucherPolicyJobDetail);
    }
  }

  private void scheduleJob(Trigger voucherPolicyTrigger, JobDetail voucherPolicyJobDetail) {
    try {
      if (!scheduler.checkExists(voucherPolicyTrigger.getKey())) {
        scheduler.scheduleJob(voucherPolicyJobDetail, voucherPolicyTrigger);
      }
    } catch (SchedulerException exception) {
      throw new VoucherPolicyScheduleException(exception);
    }
  }

  private void rescheduleJob(Trigger voucherPolicyTrigger) {
    try {
      scheduler.rescheduleJob(voucherPolicyTrigger.getKey(), voucherPolicyTrigger);
    } catch (SchedulerException exception) {
      throw new VoucherPolicyRescheduleException(exception);
    }
  }

  private Trigger getVoucherPolicyTrigger(VoucherPolicySchedule voucherPolicySchedule,
      JobDetail voucherPolicyJobDetail) {

    TriggerKey triggerKey = TriggerKey.triggerKey(voucherPolicySchedule.getId().toString(),
        VoucherPolicySchedule.class.getName());

    return buildTrigger(triggerKey, voucherPolicyJobDetail, voucherPolicySchedule.getCronExpression());
  }

}
