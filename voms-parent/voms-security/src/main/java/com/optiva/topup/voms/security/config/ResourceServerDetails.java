package com.optiva.topup.voms.security.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:security.properties")
@Log4j2
public class ResourceServerDetails {

  @Value("${security.oauth2.resource.id}")
  private String id;

  @Value("${security.oauth2.resource.secret}")
  private String secret;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public boolean isResourceServer(String clientId) {
    return Objects.equals(clientId, id);
  }

  public BaseClientDetails details() {
    BaseClientDetails baseClientDetails = new BaseClientDetails();

    String encodedResourceSecret = passwordEncoder.encode(secret);

    Collection<GrantedAuthority> clientAuthorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT"));
    Collection<String> clientScope = Collections.singletonList("trust");

    baseClientDetails.setClientId(id);
    baseClientDetails.setClientSecret(encodedResourceSecret);
    baseClientDetails.setAuthorities(clientAuthorities);
    baseClientDetails.setScope(clientScope);
    return baseClientDetails;
  }

}
