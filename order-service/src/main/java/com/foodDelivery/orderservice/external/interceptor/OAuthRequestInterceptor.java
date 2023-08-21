package com.foodDelivery.orderservice.external.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class OAuthRequestInterceptor implements RequestInterceptor{
    @Override
    public void apply(RequestTemplate template) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof AbstractOAuth2Token) {
            AbstractOAuth2Token oauth2Token = (AbstractOAuth2Token) authentication.getCredentials();
            if (oauth2Token instanceof Jwt) {
                Jwt jwtToken = (Jwt) oauth2Token;
                String accessToken = jwtToken.getTokenValue();
                template.header("Authorization", "Bearer " + accessToken);
                System.out.println(jwtToken.getClaims());
            }
        }
    }
}