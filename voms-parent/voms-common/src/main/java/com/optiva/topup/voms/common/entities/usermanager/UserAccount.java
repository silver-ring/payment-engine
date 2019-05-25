package com.optiva.topup.voms.common.entities.usermanager;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserAccount.id")
  @GenericGenerator(name = "UserAccount.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @ManyToMany
  private Set<UserRole> userRoles;

  @Column
  private String clientIpAddress;

  @Embedded
  @Column(nullable = false)
  private UserAccountInfo userAccountInfo;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserAccountStatus userAccountStatus;

  @Column(nullable = false)
  private Integer numberOfTrials;

  @Column
  private LocalDateTime lastLoginTime;

}
