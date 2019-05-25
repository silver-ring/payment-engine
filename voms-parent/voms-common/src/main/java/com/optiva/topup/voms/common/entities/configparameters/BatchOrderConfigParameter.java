package com.optiva.topup.voms.common.entities.configparameters;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.BatchOrderConfigParameterType;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchOrderConfigParameter extends ConfigParameter {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BatchOrderConfigParameterType parameter;

}
