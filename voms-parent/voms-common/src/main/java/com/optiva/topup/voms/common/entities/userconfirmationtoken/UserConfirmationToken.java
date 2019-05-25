package com.optiva.topup.voms.common.entities.userconfirmationtoken;

import com.optiva.topup.voms.common.converters.AdditionalInfoConverter;
import com.optiva.topup.voms.common.types.UserConfirmationTokeType;
import com.optiva.topup.voms.common.types.UserConfirmationTokenParameterType;
import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserConfirmationToken.id")
  @GenericGenerator(name = "UserConfirmationToken.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false, unique = true)
  private String email;

  @Convert(converter = AdditionalInfoConverter.class)
  @Column(length = 4096)
  private Map<UserConfirmationTokenParameterType, ? extends Serializable> additionalInfo;

  @Column(nullable = false)
  private UserConfirmationTokeType userConfirmationTokeType;

}
