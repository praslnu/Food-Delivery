package com.foodDelivery.restaurantservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorizeRequest -> authorizeRequest
                                .requestMatchers( "/restaurant/swagger-ui/**", "/swagger-ui/**", "/restaurant/v3/api-docs/**", "/v3/api-docs/**").permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer((oauth2ResourceServer) ->
                                oauth2ResourceServer
                                        .jwt(Customizer.withDefaults()));
        return http.build();
    }
}
