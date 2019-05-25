package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.destroyedvoucherpolicy;

import com.optiva.topup.voms.common.document.VoucherHistory;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DvpVoucherHistoryRepo extends ElasticsearchRepository<VoucherHistory, String> {

  void deleteBySerialNumberIn(List<Long> serialNumbers);

}
