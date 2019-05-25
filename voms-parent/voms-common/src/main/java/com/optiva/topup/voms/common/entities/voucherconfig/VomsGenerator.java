package com.optiva.topup.voms.common.entities.voucherconfig;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VomsGenerator {

  @Id
  private int id;

  private String name;

  private Long value;

}
