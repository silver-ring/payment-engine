package com.optiva.topup.voms.common.entities.usermanager;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserAccountInfo {

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

}
