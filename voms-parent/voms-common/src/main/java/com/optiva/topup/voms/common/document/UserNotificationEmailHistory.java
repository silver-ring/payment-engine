package com.optiva.topup.voms.common.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import com.optiva.topup.voms.common.types.UserNotificationEmailStatus;
import com.optiva.topup.voms.common.utils.DateUtils;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "user_notification_email_history", createIndex = false, type = "doc")
@Getter
@Setter
public class UserNotificationEmailHistory {

  @Id
  private String id;

  @Field
  private String sendToEmail;

  @Field
  private String sendFromEmail;

  @Field
  private String templateName;

  @Field
  private String subject;

  @Field
  private UserNotificationEmailStatus userNotificationEmailStatus;

  @Field
  private UserEmailNotificationType userEmailNotificationType;

  @Field
  private Map<String, Object> parameters;

  @Field
  private String errorMessage;

  @Field
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT)
  private LocalDateTime eventTimestamp;

}
