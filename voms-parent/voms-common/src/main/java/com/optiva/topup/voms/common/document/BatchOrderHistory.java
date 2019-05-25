package com.optiva.topup.voms.common.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import com.optiva.topup.voms.common.types.BatchOrderType;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "batch_order_history", createIndex = false, type = "doc")
@Getter
@Setter
public class BatchOrderHistory {

  @Id
  private String id;

  @Field
  private String tagId;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime executionTime;

  @Field
  private Long startSerialNumber;

  @Field
  private Long endSerialNumber;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime pendingRangeStartTime;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime pendingRangeEndTime;

  @Field
  private Integer voucherProviderId;

  @Field
  private Integer numberOfVouchers;

  @Field
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_FORMAT)
  private LocalDate expirationDate;

  @Field
  private Integer rechargePeriod;

  @Field
  private Integer rechargeValueId;

  @Field
  private Integer voucherTypeId;

  @Field
  private String layoutId;

  @Field
  private String productionFileName;

  @Field
  private Integer voucherPrinterId;

  @Field
  private List<String> errorMessages;

  @Field
  private String originalFileName;

  @Field
  private BatchOrderType batchOrderType;

  @Field
  private BatchJobHistoryStatus batchJobHistoryStatus;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime eventTimestamp;

}
