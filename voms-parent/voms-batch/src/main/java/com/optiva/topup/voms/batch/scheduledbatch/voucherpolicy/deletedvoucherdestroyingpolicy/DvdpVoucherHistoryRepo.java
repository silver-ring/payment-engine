package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import com.optiva.topup.voms.common.document.VoucherHistory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DvdpVoucherHistoryRepo extends ElasticsearchRepository<VoucherHistory, String> {

  Page<VoucherHistory> findAllBySerialNumberInOrderBySerialNumberAscEventTimestampAsc(
      List<Long> serialNumbers, Pageable page);

}
