package com.optiva.topup.voms.rest.resources.usermanager.useraccount;

import static java.util.stream.Collectors.toList;

import com.google.common.collect.Lists;
import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.utils.RestUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@BasePathAwareController
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_ACCOUNT + "')")
public class UserAccountResource {

  private final UserAccountAssembler userAccountAssembler;
  private final RepositoryEntityLinks repositoryEntityLinks;
  private final UserAccountService userAccountService;
  private final RestUtils restUtils;

  @Autowired
  public UserAccountResource(UserAccountAssembler userAccountAssembler,
      RepositoryEntityLinks repositoryEntityLinks, UserAccountService userAccountService,
      RestUtils restUtils) {
    this.userAccountAssembler = userAccountAssembler;
    this.repositoryEntityLinks = repositoryEntityLinks;
    this.userAccountService = userAccountService;
    this.restUtils = restUtils;
  }

  @GetMapping("/userAccount/search/findAllByEmail")
  public ResponseEntity<CollectionModel<Object>> getUserAccountByEmail(
      @RequestParam String email) {
    List<UserAccount> userAccounts = userAccountService.findByEmail(email);
    List<UserAccountDto> userAccountDtoes =
        Lists.newArrayList(userAccountAssembler.toCollectionModel(userAccounts).getContent());
    return new ResponseEntity<>(restUtils.getResources(userAccountDtoes, UserAccountDto.class),
        HttpStatus.OK);
  }

  @GetMapping("/userAccount/{userAccountId}/userRole")
  public ResponseEntity<CollectionModel<Object>> getUserRoles(@PathVariable Integer userAccountId) {

    List<EntityModel<UserRole>> userRoleResources = userAccountService
        .findUserRoles(userAccountId)
        .stream()
        .map(userRole -> {
          EntityModel<UserRole> resource = new EntityModel<>(userRole);
          resource.add(
              repositoryEntityLinks
                  .linkToItemResource(UserRole.class, userRole.getId())
                  .withSelfRel());
          return resource;
        }).collect(toList());

    return new ResponseEntity<>(restUtils.getResources(userRoleResources, UserRole.class), HttpStatus.OK);
  }

}
