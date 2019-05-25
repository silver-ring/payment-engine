package com.optiva.topup.voms.rest.resources.usermanager.useraccount;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import com.optiva.topup.voms.common.entities.usermanager.UserRole;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserAccountAssembler extends RepresentationModelAssemblerSupport<UserAccount, UserAccountDto> {

  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public UserAccountAssembler(RepositoryEntityLinks repositoryEntityLinks) {
    super(UserAccountResource.class, UserAccountDto.class);
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @Override
  public UserAccountDto toModel(UserAccount userAccount) {
    UserAccountDto userAccountDto = new UserAccountDto();
    userAccountDto.setEmail(userAccount.getEmail());
    userAccountDto.setFirstName(userAccount.getUserAccountInfo().getFirstName());
    userAccountDto.setLastName(userAccount.getUserAccountInfo().getLastName());
    userAccountDto.setUserAccountStatus(userAccount.getUserAccountStatus());

    Link userAccountLink = repositoryEntityLinks.linkToItemResource(UserAccount.class, userAccount.getId());
    userAccountDto.add(userAccountLink.withSelfRel());

    String userRoleName = WordUtils.uncapitalize(UserRole.class.getSimpleName());

    userAccountDto.add(new Link(userAccountLink.getHref() + "/" + userRoleName, userRoleName));

    return userAccountDto;
  }

}
