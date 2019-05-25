package com.optiva.topup.voms.db.init.initialization;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import com.optiva.topup.voms.common.document.RechargeHistory;
import com.optiva.topup.voms.common.document.RemoteFileTransferHistory;
import com.optiva.topup.voms.common.document.UserActivity;
import com.optiva.topup.voms.common.document.UserEmailDomainMigrationHistory;
import com.optiva.topup.voms.common.document.UserNotificationEmailHistory;
import com.optiva.topup.voms.common.document.VoucherHistory;
import com.optiva.topup.voms.common.document.VoucherPolicyHistory;
import com.optiva.topup.voms.db.init.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchInit implements DataInitializer {

  @Autowired
  private ElasticsearchTemplate esTemplate;

  public void init() {
    esTemplate.deleteIndex(VoucherHistory.class);
    esTemplate.createIndex(VoucherHistory.class);

    esTemplate.deleteIndex(UserActivity.class);
    esTemplate.createIndex(UserActivity.class);

    esTemplate.deleteIndex(BatchOrderHistory.class);
    esTemplate.createIndex(BatchOrderHistory.class);

    esTemplate.deleteIndex(VoucherPolicyHistory.class);
    esTemplate.createIndex(VoucherPolicyHistory.class);

    esTemplate.deleteIndex(RechargeHistory.class);
    esTemplate.createIndex(RechargeHistory.class);

    esTemplate.deleteIndex(UserNotificationEmailHistory.class);
    esTemplate.createIndex(UserNotificationEmailHistory.class);

    esTemplate.deleteIndex(RemoteFileTransferHistory.class);
    esTemplate.createIndex(RemoteFileTransferHistory.class);

    esTemplate.deleteIndex(UserEmailDomainMigrationHistory.class);
    esTemplate.createIndex(UserEmailDomainMigrationHistory.class);
  }

}
