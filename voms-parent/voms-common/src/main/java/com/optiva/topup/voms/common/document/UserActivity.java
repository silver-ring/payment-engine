package com.optiva.topup.voms.common.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.types.UserActivityType;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "user_activity", createIndex = false, type = "doc")
@Getter
@Setter
public class UserActivity {

  @Id
  private String id;

  @Field
  private String email;

  @Field
  private String target;

  @Field
  private UserActivityType userActivityType;

  @Field
  private Map<String, Object> data;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime eventTimestamp;

}
