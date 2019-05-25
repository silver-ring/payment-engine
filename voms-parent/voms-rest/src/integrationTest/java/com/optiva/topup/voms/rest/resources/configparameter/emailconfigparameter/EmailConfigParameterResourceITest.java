package com.optiva.topup.voms.rest.resources.configparameter.batchorderconfigparameter;

import static com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper.EMAIL_CONFIG_PARAMETER;
import static com.optiva.topup.voms.common.types.ConfigParameterValueType.STRING;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.optiva.topup.voms.common.BaseWebIntegrationTest;
import com.optiva.topup.voms.common.entities.configparameters.EmailConfigParameter;
import com.optiva.topup.voms.common.entities.configparameters.UserManagementConfigParameter;
import com.optiva.topup.voms.common.types.EmailConfigParameterType;
import com.optiva.topup.voms.common.types.UserManagementConfigParameterType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

class EmailConfigParameterResourceITest extends BaseWebIntegrationTest {

  private static final String ROOT_ENTITY_NAME = "emailConfigParameters";
  private static final String CONFIG_PARAMETERS_ADDRESS = "/" + ROOT_ENTITY_NAME;

  @Test
  void givenRightPermissionsAndAuthenticatedShouldSaveFine() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(EMAIL_CONFIG_PARAMETER));

    MockHttpServletRequestBuilder request = post(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    addAuthentication(request);

    assertThat(
        mockMvc.perform(request).andExpect(status().isCreated()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.CREATED.value())));
  }

  @Test
  void givenRightPermissionsAndAuthenticatedButWrongBodyContentShouldResultInBadRequestOnSave()
      throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(EMAIL_CONFIG_PARAMETER));

    MockHttpServletRequestBuilder request = post(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getWrongConfigParameter()));

    addAuthentication(request);

    assertThat(
        mockMvc.perform(request).andExpect(status().isBadRequest()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.BAD_REQUEST.value())));
  }

  @Test
  void givenRightPermissionsButNotAuthenticatedShouldUnauthorizedOnSave() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(EMAIL_CONFIG_PARAMETER));

    MockHttpServletRequestBuilder request = post(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    assertThat(
        mockMvc.perform(request).andExpect(status().isUnauthorized()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.UNAUTHORIZED.value())));
  }

  @Test
  void givenWrongPermissionsAndAuthenticatedShouldBeForbidenAndNotSave() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(WRONG_ROLE));

    MockHttpServletRequestBuilder request = post(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    addAuthentication(request);

    assertThat(mockMvc.perform(request).andExpect(status().isForbidden())
            .andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.FORBIDDEN.value())));
  }

  @Test
  void givenRightPermissionsAndAuthenticatedShouldFindAll() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(EMAIL_CONFIG_PARAMETER));

    MockHttpServletRequestBuilder request = get(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    addAuthentication(request);

    ResultActions resultActions = mockMvc.perform(request).andExpect(status().isOk());
    MvcResult result = addHateoasAspectsVerification(resultActions, ROOT_ENTITY_NAME).andReturn();

    assertThat(result.getResponse().getStatus(), is(equalTo(HttpStatus.OK.value())));
  }

  @Test
  void givenRightPermissionsButNotAuthenticatedShouldUnauthorizedOnFindAll() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(EMAIL_CONFIG_PARAMETER));

    MockHttpServletRequestBuilder request = post(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    assertThat(
        mockMvc.perform(request).andExpect(status().isUnauthorized()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.UNAUTHORIZED.value())));
  }

  @Test
  void givenWrongPermissionsAndAuthenticatedShouldBeForbidenAndNotFindAll() throws Exception {

    when(tokenStore.readAuthentication(any(OAuth2AccessToken.class)))
        .then(invocation -> getOauthTestAuthenticationWithRoles(WRONG_ROLE));

    MockHttpServletRequestBuilder request = get(CONFIG_PARAMETERS_ADDRESS)
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJson(getConfigParameter()));

    addAuthentication(request);

    assertThat(
        mockMvc.perform(request).andExpect(status().isForbidden()).andReturn().getResponse().getStatus(),
        is(equalTo(HttpStatus.FORBIDDEN.value())));
  }

  private EmailConfigParameter getConfigParameter() {
    EmailConfigParameter emailConfigParameter = EmailConfigParameter.builder()
        .parameter(EmailConfigParameterType.SMTP_HOST)
        .build();

    emailConfigParameter.setDescription("XPTO");
    emailConfigParameter.setValue("XPTO");
    emailConfigParameter.setValueType(STRING);
    emailConfigParameter.setDefaultValue("XPTO");
    emailConfigParameter.setOptional(true);

    return emailConfigParameter;
  }

  private UserManagementConfigParameter getWrongConfigParameter() {
    UserManagementConfigParameter configParameter = UserManagementConfigParameter.builder()
        .parameter(UserManagementConfigParameterType.ALLOWED_REGISTRATION_DOMAINS)
        .build();

    configParameter.setDescription("XPTO");
    configParameter.setValue("XPTO");
    configParameter.setValueType(STRING);
    configParameter.setDefaultValue("XPTO");
    configParameter.setOptional(true);

    return configParameter;
  }
}
