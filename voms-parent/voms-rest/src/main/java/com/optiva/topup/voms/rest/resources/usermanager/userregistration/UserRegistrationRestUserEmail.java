package com.optiva.topup.voms.rest.resources.usermanager.userregistration;

import static com.optiva.topup.voms.common.types.ConfigParameterValueType.getValueAsList;
import static com.optiva.topup.voms.common.types.UserConfirmationTokenParameterType.USER_ROLES;
import static com.optiva.topup.voms.common.types.UserManagementConfigParameterType.ALLOWED_REGISTRATION_DOMAINS;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import com.optiva.topup.voms.common.exceptions.UserAccountNotFoundException;
import com.optiva.topup.voms.common.repositories.UserConfirmationTokenRepo;
import com.optiva.topup.voms.common.repositories.configparameters.UserManagementConfigParameterRepo;
import com.optiva.topup.voms.common.repositories.user.UserAccountRepo;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.common.support.WithKafkaSupport;
import com.optiva.topup.voms.common.support.WithTokenUserEmailNotification;
import com.optiva.topup.voms.common.types.UserConfirmationTokeType;
import com.optiva.topup.voms.common.types.UserConfirmationTokenParameterType;
import com.optiva.topup.voms.common.types.UserEmailNotificationType;
import com.optiva.topup.voms.rest.utils.RestUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_REGISTRATION + "')")
public class UserRegistrationRestUserEmail implements WithTokenUserEmailNotification, WithKafkaSupport {

  private final RestUtils restUtils;
  private final UserAccountRepo userAccountRepo;
  private final UserConfirmationTokenRepo userConfirmationTokenRepo;
  private final UserManagementConfigParameterRepo configParameterRepo;

  @Autowired
  public UserRegistrationRestUserEmail(RestUtils restUtils, UserAccountRepo userAccountRepo,
      UserConfirmationTokenRepo userConfirmationTokenRepo,
      UserManagementConfigParameterRepo configParameterRepo) {
    this.restUtils = restUtils;
    this.userAccountRepo = userAccountRepo;
    this.userConfirmationTokenRepo = userConfirmationTokenRepo;
    this.configParameterRepo = configParameterRepo;
  }

  @PostMapping("/userRegistration")
  public void userRegistration(@RequestBody UserRegistrationDto userRegistrationDto) {
    if (userAccountRepo.existsByEmail(userRegistrationDto.getEmail())) {
      throw new UserAccountNotFoundException();
    }

    String allowedRegistrationDomains =
        configParameterRepo.findByParameter(ALLOWED_REGISTRATION_DOMAINS).getValue();

    String enteredDomain = userRegistrationDto.getEmail().split("@")[1];

    Boolean isAcceptedDomain = getValueAsList(allowedRegistrationDomains)
        .stream().anyMatch(domain -> StringUtils.equals(domain, enteredDomain));

    if (!isAcceptedDomain) {
      throw new DomainNotAllowedException();
    }

    List<Integer> userRolesIds = restUtils.extractIdsFromLinks(userRegistrationDto.getUserRoles());

    Map<UserConfirmationTokenParameterType, Serializable> additionalInfo = new HashMap<>();
    additionalInfo.put(USER_ROLES, new ArrayList<>(userRolesIds));

    UserConfirmationToken userConfirmationToken = userConfirmationTokenRepo
        .findByEmail(userRegistrationDto.getEmail())
        .orElse(null);

    if (userConfirmationToken == null) {
      userConfirmationToken = new UserConfirmationToken();
      userConfirmationToken.setEmail(userRegistrationDto.getEmail());
      userConfirmationToken.setToken(UUID.randomUUID().toString());
      userConfirmationToken.setUserConfirmationTokeType(UserConfirmationTokeType.USER_REGISTRATION);
    }
    userConfirmationToken.setAdditionalInfo(additionalInfo);

    UserConfirmationToken newUserConfirmationToken = userConfirmationTokenRepo.save(userConfirmationToken);

    sendTokenUserEmailNotificationTopic(newUserConfirmationToken,
        UserEmailNotificationType.USER_REGISTRATION);

  }

}
