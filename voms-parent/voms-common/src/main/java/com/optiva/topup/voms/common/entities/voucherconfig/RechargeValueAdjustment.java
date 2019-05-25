package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.types.AmountType;
import com.optiva.topup.voms.common.types.Status;
import java.time.LocalDate;
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
public abstract class RechargeValueAdjustment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RechargeValueAdjustment.id")
  @GenericGenerator(name = "RechargeValueAdjustment.id", strategy = "native")
  private Integer id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private Double amount;
  @Column(nullable = false)
  private LocalDate startDate;
  @Column(nullable = false)
  private LocalDate endDate;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AmountType amountType;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

}
