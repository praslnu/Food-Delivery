package com.foodDelivery.userservice.external.client;

import com.foodDelivery.userservice.response.RestaurantResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT-SERVICE/restaurant")
public interface RestaurantClient{
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable("id") Long restaurantId);

    default String fallbackMethod(Throwable throwable) {
        return "Restaurant Service is down";
    }
}
