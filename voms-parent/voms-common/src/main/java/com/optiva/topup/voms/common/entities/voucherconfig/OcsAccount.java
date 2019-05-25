package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.Status;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class OcsAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OcsAccount.id")
  @GenericGenerator(name = "OcsAccount.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false, unique = true)
  private Integer idAtOcs;

  @Embedded
  @Column(nullable = false)
  private Currency currency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

}
