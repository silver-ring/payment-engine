package com.optiva.topup.voms.common.entities.orders;

import com.optiva.topup.voms.common.types.BatchJobScheduleStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
@Setter
public abstract class BatchOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatchOrder.id")
  @GenericGenerator(name = "BatchOrder.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String tagId;

  @Column(nullable = false)
  private LocalDateTime executionTime;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BatchJobScheduleStatus batchJobScheduleStatus;

}
