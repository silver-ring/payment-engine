package com.optiva.topup.voms.common.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.types.RechargeHistoryStatus;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "recharge_history", createIndex = false, type = "doc")
@Getter
@Setter
public class RechargeHistory {

  @Id
  private String id;

  @Field
  private String msisdn;

  @Field
  private Long serialNumber;

  @Field
  private Long voucherId;

  @Field
  private String transactionId;

  @Field
  private Integer msisdnProviderId;

  @Field
  private String ccId;

  @Field
  private Double amount;

  @Field
  private String currency;

  @Field
  private Integer providerIdVoucher;

  @Field
  private Integer balanceExtension;

  @Field
  private Integer voucherType;

  @Field
  private Integer errorCode;

  @Field
  private Integer voucherExpiryDate;

  @Field
  private Integer accountId;

  @Field
  private RechargeHistoryStatus rechargeHistoryStatus;

  @Field
  private String errorMessage;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime eventTimestamp;

}
