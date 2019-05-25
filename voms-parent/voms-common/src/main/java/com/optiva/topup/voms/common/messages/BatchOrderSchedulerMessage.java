package com.optiva.topup.voms.common.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import com.optiva.topup.voms.common.types.BatchJobScheduleStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchOrderSchedulerMessage {

  private Integer orderId;
  private String taskTopic;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime executionTime;
  private String tagId;
  private BatchJobScheduleStatus orderStatus;
  private Class<? extends BatchOrder> orderEntity;

}
