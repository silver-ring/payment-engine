package com.optiva.topup.voms.common.support;

public interface WithVoucherPolicyTopicsSupport extends WithKafkaSupport {

  String DESTROYED_VOUCHER_POLICY_TASK_TOPIC = "DestroyedVoucherPolicyTaskTopic";
  String DELETED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC =
      "DeletedVoucherDestroyingPolicyTaskTopic";
  String CREATED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC =
      "CreatedVoucherDestroyingPolicyTaskTopic";
  String USED_VOUCHER_DELETION_POLICY_TASK_TOPIC = "UsedVoucherDeletionPolicyTaskTopic";
  String BLOCKED_VOUCHER_DELETION_POLICY_TASK_TOPIC =
      "BlockedVoucherDeletionPolicyTaskTopic";
  String ACTIVE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC =
      "ActiveVoucherBlockingPolicyTaskTopic";
  String PRODUCTION_VOUCHER_DESTROYING_POLICY_TASK_TOPIC =
      "ProductionVoucherDestroyingPolicyTaskTopic";
  String PENDING_USAGE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC =
      "PendingUsageVoucherBlockingPolicyTaskTopic";
  String VOUCHER_POLICY_RESCHEDULE_TOPIC = "VoucherPolicyRescheduleTopic";

  default void sendVoucherPolicyRescheduleTopic(Integer voucherPolicyScheduleId) {
    sendTopic(VOUCHER_POLICY_RESCHEDULE_TOPIC, voucherPolicyScheduleId);
  }

}
