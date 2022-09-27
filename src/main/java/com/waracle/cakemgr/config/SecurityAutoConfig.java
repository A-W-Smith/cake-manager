package com.waracle.cakemgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/** Configure security for all profiles apart from noAuth */
@Configuration
@EnableWebSecurity
@Profile("!noAuth")
public class SecurityAutoConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
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
}
