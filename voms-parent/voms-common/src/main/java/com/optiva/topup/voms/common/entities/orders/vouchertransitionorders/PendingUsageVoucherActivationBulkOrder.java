package com.optiva.topup.voms.common.entities.orders.vouchertransitionorders;

import com.optiva.topup.voms.common.entities.orders.BulkOrder;
import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class PendingUsageVoucherActivationBulkOrder extends BulkOrder {

  @Column
  private LocalDateTime pendingRangeStartTime;

  @Column
  private LocalDateTime pendingRangeEndTime;

}
