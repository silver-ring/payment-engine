package com.optiva.topup.voms.common.entities.configparameters;

import com.optiva.topup.voms.common.types.ConfigParameterType;
import com.optiva.topup.voms.common.types.ConfigParameterValueType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public abstract class ConfigParameter {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ConfigParameter.id")
  @GenericGenerator(name = "ConfigParameter.id", strategy = "native")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ConfigParameterValueType valueType;

  @Column(nullable = false)
  private String defaultValue;

  @Column(nullable = false)
  private String value;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Boolean optional;

  public abstract ConfigParameterType getParameter();

}
