package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.Status;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RechargeValue {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RechargeValue.id")
  @GenericGenerator(name = "RechargeValue.id", strategy = "native")
  private Integer id;
  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = false)
  private Double value;
  @Column(nullable = false)
  private Double valueTaxation;
  @Column(nullable = false)
  private Double valuePromotion;

  @ManyToOne(optional = false)
  private OcsAccount ocsAccount;

  @ManyToMany
  private Set<Promotion> promotions;

  @ManyToMany
  private Set<Taxation> taxations;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

}
