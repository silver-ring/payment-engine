package com.optiva.topup.voms.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DefaultClientDetailsService implements ClientDetailsService {

  private final ResourceServerDetails resourceServerDetails;
  private final ClientDetailsLoader clientLoader;

  @Autowired
  public DefaultClientDetailsService(ResourceServerDetails resourceServerDetails,
      ClientDetailsLoader clientLoader) {
    this.resourceServerDetails = resourceServerDetails;
    this.clientLoader = clientLoader;
  }

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

    if (resourceServerDetails.isResourceServer(clientId)) {
      BaseClientDetails clientDetails = resourceServerDetails.details();
      log.debug("Retrieving client details using the resource server details.");
      return clientDetails;
    }

    ClientDetails clientDetails = clientLoader.load(clientId);
    log.debug("Retrieving client details using the client details loader.");
    return clientDetails;
  }
}
