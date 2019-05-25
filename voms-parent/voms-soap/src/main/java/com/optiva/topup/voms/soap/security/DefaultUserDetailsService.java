package com.optiva.topup.voms.soap.security;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserAuthority;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

  private final SUserAccountRepo userAccountRepo;
  private final SUserRoleRepo userRoleRepo;

  @Autowired
  public DefaultUserDetailsService(SUserAccountRepo userAccountRepo, SUserRoleRepo userRoleRepo) {
    this.userAccountRepo = userAccountRepo;
    this.userRoleRepo = userRoleRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserAccount user = userAccountRepo.findByEmailAndUserAccountStatus(username, UserAccountStatus.ACTIVE);

    if (user == null) {
      throw new UsernameNotFoundException("User Is Not Exist");
    }

    Set<GrantedAuthority> apiUserAuthorities = userRoleRepo.findAllById(user.getUserRoles()
        .stream()
        .filter(userRole -> userRole.getStatus() == Status.ACTIVE)
        .map(UserRole::getId)
        .collect(Collectors.toList()))
        .stream()
        .map(UserRole::getApiUserAuthorities)
        .flatMap(Collection::stream)
        .collect(Collectors.toList())
        .stream()
        .map(UserAuthority::getName)
        .distinct()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());

    if (apiUserAuthorities.isEmpty()) {
      throw new AccessDeniedException("Unauthorized");
    }

    return new User(user.getEmail(), user.getPassword(), apiUserAuthorities);
  }

}
