package com.optiva.topup.voms.common.entities.configparameters;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.VoucherPolicyConfigParameterType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class VoucherPolicyConfigParameter extends ConfigParameter {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private VoucherPolicyConfigParameterType parameter;

}
