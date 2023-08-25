package com.foodDelivery.restaurantservice.external.client;

import com.foodDelivery.restaurantservice.exception.CustomException;
import com.foodDelivery.restaurantservice.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.response.SuccessResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @CircuitBreaker(name = "external", fallbackMethod = "fallbackUserCart")
    @PutMapping("/cart")
    public ResponseEntity<String> addFoodToCart(@RequestBody CartDetailsRequest cartDetailsRequest);

    @CircuitBreaker(name = "external", fallbackMethod = "fallback")
    @PostMapping("/favourites/{restaurantId}")
    public ResponseEntity<SuccessResponse> addToFavourites(@PathVariable Long restaurantId);

    default ResponseEntity<String> fallbackUserCart(Exception e) {
        throw new CustomException("User Service is not available", 500);
    }
}