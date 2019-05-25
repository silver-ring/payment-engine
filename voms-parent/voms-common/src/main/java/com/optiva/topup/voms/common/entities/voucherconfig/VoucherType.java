package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.Status;
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
public class VoucherType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VoucherType.id")
  @GenericGenerator(name = "VoucherType.id", strategy = "native")
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer idAtIn;

  @Enumerated(EnumType.STRING)
  private Status status;

}
