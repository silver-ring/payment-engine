package com.optiva.topup.voms.common.entities.usermanager;

import com.optiva.topup.voms.common.types.BatchJobScheduleStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
public class UserEmailDomainMigration {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserEmailDomainMigration.id")
  @GenericGenerator(name = "UserEmailDomainMigration.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String tagId;

  @Column(nullable = false)
  private LocalDateTime executionTime;

  @Column(nullable = false)
  private String migrateFromDomain;

  @Column(nullable = false)
  private String migrateToDomain;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private BatchJobScheduleStatus batchJobScheduleStatus;

}
