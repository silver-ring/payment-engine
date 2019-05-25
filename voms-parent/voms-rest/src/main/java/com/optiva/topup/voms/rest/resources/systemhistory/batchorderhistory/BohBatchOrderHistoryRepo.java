package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BohBatchOrderHistoryRepo extends SimpleElasticsearchRepository<BatchOrderHistory, String> {

  public BohBatchOrderHistoryRepo(ElasticsearchOperations elasticsearchOperations) {
    super(elasticsearchOperations);
  }

}
