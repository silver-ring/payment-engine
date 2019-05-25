package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.document.VoucherHistory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VhVoucherHistoryRepo extends SimpleElasticsearchRepository<VoucherHistory, String> {

  public VhVoucherHistoryRepo(ElasticsearchOperations elasticsearchOperations) {
    super(elasticsearchOperations);
  }

}
