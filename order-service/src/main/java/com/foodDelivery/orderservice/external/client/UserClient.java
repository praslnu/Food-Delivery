package com.foodDelivery.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @DeleteMapping("/cart/restaurant/{restaurantId}")
    public void removeCartItems(@PathVariable long restaurantId);
}
