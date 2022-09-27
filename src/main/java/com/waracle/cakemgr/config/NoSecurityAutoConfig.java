package com.waracle.cakemgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/** Disable OAuth when running in noAuth profile */
@Configuration
@EnableWebSecurity
@Profile("noAuth")
public class NoSecurityAutoConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().build();
  }
}
