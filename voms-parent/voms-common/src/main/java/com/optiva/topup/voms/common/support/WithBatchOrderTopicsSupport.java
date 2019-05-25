package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.messages.BatchOrderSchedulerMessage;

public interface WithBatchOrderTopicsSupport extends WithKafkaSupport {

  String BATCH_ORDER_SCHEDULER_TOPIC = "BatchOrderSchedulerTopic";

  String CREATED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC = "CreatedVoucherModificationBulkOrderTaskTopic";
  String CREATED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC = "CreatedVoucherModificationListOrderTaskTopic";

  String BLOCKED_VOUCHER_MODIFICATION_BULK_ORDER_TASK_TOPIC = "BlockedVoucherModificationBulkOrderTaskTopic";
  String BLOCKED_VOUCHER_MODIFICATION_LIST_ORDER_TASK_TOPIC = "BlockedVoucherModificationListOrderTaskTopic";

  String VOUCHER_CREATION_BATCH_ORDER_TASK_TOPIC = "VoucherCreationBatchOrderTaskTopic";
  String PRODUCTION_FILE_CREATION_BULK_ORDER_TASK_TOPIC = "ProductionFileCreationBulkOrderTaskTopic";
  String PRODUCTION_FILE_CREATION_LIST_ORDER_TASK_TOPIC = "ProductionFileCreationListOrderTaskTopic";

  String CREATED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC = "CreatedVoucherActivationBulkOrderTaskTopic";
  String CREATED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC = "CreatedVoucherActivationListOrderTaskTopic";

  String CREATED_VOUCHER_DESTROY_BULK_ORDER_TASK_TOPIC = "CreatedVoucherDestroyBulkOrderTaskTopic";
  String CREATED_VOUCHER_DESTROY_LIST_ORDER_TASK_TOPIC = "CreatedVoucherDestroyListOrderTaskTopic";

  String ACTIVE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC = "ActiveVoucherBlockingBulkOrderTaskTopic";
  String ACTIVE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC = "ActiveVoucherBlockingListOrderTaskTopic";

  String BLOCKED_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC = "BlockedVoucherActivationBulkOrderTaskTopic";
  String BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC = "BlockedVoucherActivationListOrderTaskTopic";

  String PENDING_USAGE_VOUCHER_ACTIVATION_BULK_ORDER_TASK_TOPIC =
      "PendingUsageVoucherActivationBulkOrderTaskTopic";
  String PENDING_USAGE_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC =
      "PendingUsageVoucherActivationListOrderTaskTopic";

  String PENDING_USAGE_VOUCHER_BLOCKING_BULK_ORDER_TASK_TOPIC =
      "PendingUsageVoucherBlockingBulkOrderTaskTopic";
  String PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC =
      "PendingUsageVoucherBlockingListOrderTaskTopic";

  String PENDING_USAGE_VOUCHER_USING_BULK_ORDER_TASK_TOPIC = "PendingUsageVoucherUsingBulkOrderTaskTopic";
  String PENDING_USAGE_VOUCHER_USING_LIST_ORDER_TASK_TOPIC = "PendingUsageVoucherUsingListOrderTaskTopic";


  default void sendBatchOrderScheduleTopic(BatchOrderSchedulerMessage batchOrderSchedulerMessage) {
    sendTopic(BATCH_ORDER_SCHEDULER_TOPIC, batchOrderSchedulerMessage);
  }

}
