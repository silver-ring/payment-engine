package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.converters.VoucherIdConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Voucher {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Voucher.id")
  @GenericGenerator(name = "Voucher.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true, updatable = false)
  @Convert(converter = VoucherIdConverter.class)
  private Long voucherId;

  @Column(nullable = false, unique = true, updatable = false)
  private Long serialNumber;

}
