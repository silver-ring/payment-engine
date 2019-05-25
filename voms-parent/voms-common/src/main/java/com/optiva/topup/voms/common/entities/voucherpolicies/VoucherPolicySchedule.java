package com.optiva.topup.voms.common.entities.voucherpolicies;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.VoucherPolicyType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@EntityListeners({AuditEntityListener.class})
public class VoucherPolicySchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VoucherPolicySchedule.id")
  @GenericGenerator(name = "VoucherPolicySchedule.id", strategy = "native")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VoucherPolicyType voucherPolicyType;

  @Column(nullable = false)
  private String defaultCronExpression;

  @Column(nullable = false)
  private String cronExpression;

  @Column(nullable = false)
  private String description;

}
