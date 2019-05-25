package com.optiva.topup.voms.soap.config;

import static com.optiva.topup.voms.soap.elements.ElementsConfigParametersHelper.VOMS_NAME_SPACE;

import java.util.List;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SpringSecurityPasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebServiceConfig extends WsConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final DefaultUsernameTokenValidator defaultUsernameTokenValidator;

  @Autowired
  public WebServiceConfig(UserDetailsService userDetailsService,
      DefaultUsernameTokenValidator defaultUsernameTokenValidator) {
    this.userDetailsService = userDetailsService;
    this.defaultUsernameTokenValidator = defaultUsernameTokenValidator;
  }

  @Bean
  public SpringSecurityPasswordValidationCallbackHandler securityCallbackHandler() {
    SpringSecurityPasswordValidationCallbackHandler callbackHandler =
        new SpringSecurityPasswordValidationCallbackHandler();
    callbackHandler.setUserDetailsService(userDetailsService);
    return callbackHandler;
  }

  @Bean
  public Wss4jSecurityInterceptor securityInterceptor() {

    WSSConfig wssConfig = WSSConfig.getNewInstance();
    wssConfig.setValidator(WSConstants.USERNAME_TOKEN, defaultUsernameTokenValidator);

    Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
    securityInterceptor.setWssConfig(wssConfig);
    securityInterceptor.setValidationActions("UsernameToken");
    securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
    return securityInterceptor;
  }

  @Override
  public void addInterceptors(List interceptors) {
    interceptors.add(securityInterceptor());
  }

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
      ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean("voms")
  public DefaultWsdl11Definition defaultWsdlDefinition() {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("VomsPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(VOMS_NAME_SPACE);
    wsdl11Definition.setSchemaCollection(courseSchema());
    return wsdl11Definition;
  }

  @Bean
  public XsdSchemaCollection courseSchema() {
    CommonsXsdSchemaCollection xsdSchemaCollection = new CommonsXsdSchemaCollection(
        new ClassPathResource("voms.xsd"));
    xsdSchemaCollection.setInline(true);
    return xsdSchemaCollection;
  }

}
