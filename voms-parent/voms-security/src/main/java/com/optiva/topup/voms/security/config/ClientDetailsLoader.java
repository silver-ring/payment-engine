package com.optiva.topup.voms.security.config;

import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserAuthority;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.common.types.UserAccountStatus;
import com.optiva.topup.voms.common.types.UserManagementConfigParameterType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class ClientDetailsLoader {

  private final UserAccountRepo userAccountRepo;
  private final UserManagementConfigParameterRepo configParameterRepo;

  @Autowired
  public ClientDetailsLoader(UserAccountRepo userAccountRepo,
      UserManagementConfigParameterRepo configParameterRepo) {
    this.userAccountRepo = userAccountRepo;
    this.configParameterRepo = configParameterRepo;
  }

  @Transactional
  public ClientDetails load(String clientId) {

    log.debug("Loading user with client id");

    UserAccount userAccount = userAccountRepo
        .findByEmail(clientId)
        .orElseThrow(() -> new ClientRegistrationException("Client Is Not Exist"));

    log.debug("User account: {}", userAccount);

    if (userAccount.getUserAccountStatus() == UserAccountStatus.INACTIVE) {
      throw new ClientRegistrationException("User is inactive");
    }

    UserManagementConfigParameter userManagementConfigParameter = configParameterRepo
        .findByParameter(UserManagementConfigParameterType.USER_ACCOUNT_LOCK_DURATION);
    Integer lockDuration = Integer.parseInt(userManagementConfigParameter.getValue());

    if (userAccount.getUserAccountStatus() == UserAccountStatus.LOCKED
        && userAccount.getLastLoginTime().plusMinutes(lockDuration).isAfter(LocalDateTime.now())) {
      throw new ClientRegistrationException("User is locked");
    }

    Set<GrantedAuthority> guiUserAuthorities = userAccount.getUserRoles()
        .stream()
        .filter(userRole -> userRole.getStatus() == Status.ACTIVE)
        .map(UserRole::getGuiUserAuthorities)
        .flatMap(Collection::stream)
        .collect(Collectors.toList())
        .stream()
        .map(UserAuthority::getName)
        .distinct()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());

    if (guiUserAuthorities.isEmpty()) {
      log.debug("User has no Authorities");
      throw new ClientRegistrationException("Unauthorized");
    }

    Map<String, Object> additionalInformation = new HashMap<>();
    additionalInformation.put("firstName", userAccount.getUserAccountInfo().getFirstName());
    additionalInformation.put("lastName", userAccount.getUserAccountInfo().getLastName());

    Collection<String> userScope = Arrays.asList("read", "write");

    BaseClientDetails baseClientDetails = new BaseClientDetails();
    baseClientDetails.setClientId(userAccount.getEmail());
    baseClientDetails.setClientSecret(userAccount.getPassword());
    baseClientDetails.setAuthorities(guiUserAuthorities);
    baseClientDetails.setAdditionalInformation(additionalInformation);
    baseClientDetails.setScope(userScope);

    log.debug("returning base client details.");

    return baseClientDetails;
  }

}
