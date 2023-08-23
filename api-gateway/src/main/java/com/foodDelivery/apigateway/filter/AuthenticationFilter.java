package com.foodDelivery.apigateway.filter;

import com.foodDelivery.apigateway.exception.AccessDeniedException;
import com.foodDelivery.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AccessDeniedException("missing authorization header");
                }
                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }
                try {
                    if (!jwtUtil.isTokenValid(token)){
                        throw new AccessDeniedException("Invalid Token");
                    }
                    System.out.println("Here is the username -> " + jwtUtil.extractUsername(token));
                    System.out.println(jwtUtil.extractAllClaims(token));
                } catch (Exception e) {
                    throw new AccessDeniedException("Access Denied!");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}