package com.optiva.topup.voms.common.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.types.BatchJobHistoryStatus;
import com.optiva.topup.voms.common.types.VoucherPolicyType;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "voucher_policy_history", createIndex = false, type = "doc")
@Getter
@Setter
public class VoucherPolicyHistory {

  @Id
  private String id;

  @Field
  private VoucherPolicyType voucherPolicyType;

  @Field
  private String description;

  @Field
  private BatchJobHistoryStatus batchJobHistoryStatus;

  @Field
  private List<String> errorMessages;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime eventTimestamp;

}
