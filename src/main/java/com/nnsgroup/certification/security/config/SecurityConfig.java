package com.nnsgroup.certification.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        http
            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer
                    .getEndpointsMatcher()))
            .with(authorizationServerConfigurer, config -> {});

        http.formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .formLogin(withDefaults())
            .oauth2ResourceServer(oauth -> oauth.jwt());

        return http.build();
    }
}
