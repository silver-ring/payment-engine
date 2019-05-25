package com.optiva.topup.voms.common.entities.usermanager;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.Status;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserRole.id")
  @GenericGenerator(name = "UserRole.id", strategy = "native")
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToMany
  private Set<GuiUserAuthority> guiUserAuthorities;

  @ManyToMany
  private Set<ApiUserAuthority> apiUserAuthorities;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

}
