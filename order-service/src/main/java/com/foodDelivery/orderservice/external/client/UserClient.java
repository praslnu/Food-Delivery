package com.foodDelivery.orderservice.external.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    @DeleteMapping("/cart/restaurant/{restaurantId}")
    public void removeCartItems(@PathVariable long restaurantId);

    default void fallbackMethod(Throwable throwable) throws Exception {
        throw new Exception("User Service is down");
    }
}
