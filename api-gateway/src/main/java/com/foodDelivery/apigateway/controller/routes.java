package com.foodDelivery.apigateway.controller;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

public class routes{
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/restaurant-service/v3/api-docs").and().method(HttpMethod.GET).uri("lb://restaurant-service"))
                .build();
    }
}
