package com.waracle.cakemgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/** Configure security for all profiles apart from noAuth */
@Configuration
@EnableWebSecurity
public class SecurityAutoConfig {
  @Profile("!noAuth")
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http, ClientRegistrationRepository repo) throws Exception {
    return http.authorizeRequests(
            a ->
                a.antMatchers("/", "/index.js", "/index.html", "/style.css", "/webjars/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(e -> e.authenticationEntryPoint(new UnauthorisedEntryPoint()))
        .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .oauth2Login()
        .and()
        .build();
  }

  @Profile("noAuth")
  @Bean
  public SecurityFilterChain noAuthSecurityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().build();
  }

  @Profile("!noAuth")
  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(githubClientRegistration());
  }

  private ClientRegistration githubClientRegistration() {
    return ClientRegistration.withRegistrationId("github")
        .clientId("7d5f37a666618c57093b")
        .clientSecret("9640b084bedbdb541b57a550deffba7ce4ecd1e7")
        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .scope("read:user")
        .tokenUri("https://github.com/login/oauth/access_token")
        .authorizationUri("https://github.com/login/oauth/authorize")
        .tokenUri("https://github.com/login/oauth/access_token")
        .userInfoUri("https://api.github.com/user")
        .userInfoAuthenticationMethod(AuthenticationMethod.HEADER)
        .userNameAttributeName("id")
        .clientName("GitHub")
        .build();
  }
}
