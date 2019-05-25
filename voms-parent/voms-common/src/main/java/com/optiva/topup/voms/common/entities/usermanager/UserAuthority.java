package com.optiva.topup.voms.common.entities.usermanager;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@Getter
@Setter
public class UserAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserAuthority.id")
  @GenericGenerator(name = "UserAuthority.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

}
