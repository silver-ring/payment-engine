package com.optiva.topup.voms.common.entities.configparameters;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.EmailConfigParameterType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfigParameter extends ConfigParameter {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EmailConfigParameterType parameter;

}
