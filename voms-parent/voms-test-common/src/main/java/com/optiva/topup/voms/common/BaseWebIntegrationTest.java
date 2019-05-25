package com.optiva.topup.voms.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.google.gson.Gson;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseWebIntegrationTest extends BaseKafkaIntegrationTest {

  protected static final String ADMIN_AT_VOMS_COM = "admin@voms.com";
  protected static final String CHANGE_PASSWORD_TOKEN = "1234567890";
  private static final MediaType HATEOAS_MEDIA_TYPE =
      new MediaType("application", "hal+json", StandardCharsets.UTF_8);
  protected static final String WRONG_ROLE = "WRONG_ROLE";

  @MockBean
  public TokenStore tokenStore;
  protected MockMvc mockMvc;
  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .alwaysDo(print())
        .build();

    when(tokenStore.readAuthentication(anyString())).then(invocation -> getOauthTestAuthentication());
    when(tokenStore.readAccessToken(anyString())).then(invocation -> mock(OAuth2AccessToken.class));
  }

  protected OAuth2Authentication getOauthTestAuthentication() {
    return new OAuth2Authentication(getOauth2Request(), getAuthentication());
  }

  protected OAuth2Authentication getOauthTestAuthenticationWithRoles(String... roles) {
    return new OAuth2Authentication(getOauth2Request(), getAuthentication(roles));
  }

  private OAuth2Request getOauth2Request() {
    String clientId = "oauth-client-id";
    Map<String, String> requestParameters = Collections.emptyMap();
    String redirectUrl = "http://my-redirect-url.com";
    Set<String> responseTypes = Collections.emptySet();
    Set<String> scopes = Collections.emptySet();
    Set<String> resourceIds = Collections.emptySet();
    Map<String, Serializable> extensionProperties = Collections.emptyMap();
    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("Everything");

    return new OAuth2Request(requestParameters, clientId, authorities,
        true, scopes, resourceIds, redirectUrl, responseTypes, extensionProperties);
  }

  private Authentication getAuthentication() {
    return getAuthentication(new String[]{"everything"});
  }

  private Authentication getAuthentication(String[] roles) {
    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);

    HashMap<String, String> details = new HashMap<>();
    details.put("user_name", "bwatkins");
    details.put("email", "bwatkins@test.org");
    details.put("name", "Brian Watkins");

    TestingAuthenticationToken token = new TestingAuthenticationToken(ADMIN_AT_VOMS_COM, null, authorities);
    token.setAuthenticated(true);
    token.setDetails(details);

    return token;
  }

  protected OAuth2ClientContext getOauth2ClientContext() {
    OAuth2ClientContext mockClient = mock(OAuth2ClientContext.class);
    when(mockClient.getAccessToken()).thenReturn(new DefaultOAuth2AccessToken("my-fun-token"));

    return mockClient;
  }

  protected void addAuthentication(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    mockHttpServletRequestBuilder.header("Authorization", "bearer 079d7714-93b9-4627-9575-1edd0ec4df3b")
        .with(authentication(getOauthTestAuthentication()))
        .sessionAttr("scopedTarget.oauth2ClientContext", getOauth2ClientContext());
  }

  protected String asJson(Object object) {
    return new Gson().toJson(object);
  }

  protected ResultActions addHateoasAspectsVerification(ResultActions resultActions, String rootEntityName)
      throws Exception {
    return resultActions
        .andExpect(content().contentType(HATEOAS_MEDIA_TYPE))
        .andExpect(jsonPath("$._embedded." + rootEntityName, hasSize(0)))
        .andExpect(jsonPath("$._links.self.href",
            is("http://localhost/" + rootEntityName + "{?page,size,sort}")))
        .andExpect(jsonPath("$._links.self.templated", is(true)))
        .andExpect(
            jsonPath("$._links.profile.href", is("http://localhost/profile/" + rootEntityName)))
        .andExpect(jsonPath("$.page.size", is(20)))
        .andExpect(jsonPath("$.page.totalElements", is(0)))
        .andExpect(jsonPath("$.page.totalPages", is(0)))
        .andExpect(jsonPath("$.page.number", is(0)));
  }
}
