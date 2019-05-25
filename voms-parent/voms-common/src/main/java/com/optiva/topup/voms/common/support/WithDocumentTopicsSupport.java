package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.document.RechargeHistory;
import com.optiva.topup.voms.common.document.RemoteFileTransferHistory;
import com.optiva.topup.voms.common.document.UserActivity;
import com.optiva.topup.voms.common.document.UserEmailDomainMigrationHistory;
import com.optiva.topup.voms.common.document.UserNotificationEmailHistory;
import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.document.VoucherPolicyHistory;

public interface WithDocumentTopicsSupport extends WithKafkaSupport {

  String VOUCHER_HISTORY_DOCUMENT_TOPIC = "voucher_history";
  String USER_ACTIVITY_DOCUMENT_TOPIC = "user_activity";
  String BATCH_ORDER_HISTORY_DOCUMENT_TOPIC = "batch_order_history";
  String VOUCHER_POLICY_HISTORY_TOPIC = "voucher_policy_history";
  String RECHARGE_HISTORY_TOPIC = "recharge_history";
  String REMOTE_FILE_TRANSFER_HISTORY_TOPIC = "remote_file_transfer_history";
  String USER_NOTIFICATION_EMAIL_HISTORY_TOPIC = "user_notification_email_history";
  String USER_EMAIL_DOMAIN_MIGRATION_HISTORY_TOPIC = "user_email_domain_migration_history";


  default void sendDocumentMessage(VoucherHistory historyDocument) {
    sendTopic(VOUCHER_HISTORY_DOCUMENT_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(UserActivity historyDocument) {
    sendTopic(USER_ACTIVITY_DOCUMENT_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(BatchOrderHistory historyDocument) {
    sendTopic(BATCH_ORDER_HISTORY_DOCUMENT_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(VoucherPolicyHistory historyDocument) {
    sendTopic(VOUCHER_POLICY_HISTORY_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(RechargeHistory historyDocument) {
    sendTopic(RECHARGE_HISTORY_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(RemoteFileTransferHistory historyDocument) {
    sendTopic(REMOTE_FILE_TRANSFER_HISTORY_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(UserNotificationEmailHistory historyDocument) {
    sendTopic(USER_NOTIFICATION_EMAIL_HISTORY_TOPIC, historyDocument);
  }

  default void sendDocumentMessage(UserEmailDomainMigrationHistory historyDocument) {
    sendTopic(USER_EMAIL_DOMAIN_MIGRATION_HISTORY_TOPIC, historyDocument);
  }

}
