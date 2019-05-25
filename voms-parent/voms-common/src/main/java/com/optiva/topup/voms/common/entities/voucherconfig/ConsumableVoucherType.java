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
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class ConsumableVoucherType {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ConsumableVoucherType.id")
  @GenericGenerator(name = "ConsumableVoucherType.id", strategy = "native")
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @ManyToOne(optional = false)
  private MsisdnProvider msisdnProvider;

  @ManyToOne(optional = false)
  private VoucherProvider voucherProvider;

  @ManyToOne(optional = false)
  private VoucherType voucherType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

}
