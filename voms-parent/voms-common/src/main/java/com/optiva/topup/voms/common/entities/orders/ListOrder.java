package com.optiva.topup.voms.common.entities.orders;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Getter
@Setter
public abstract class ListOrder extends BatchOrder {

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private String originalFileName;

}
