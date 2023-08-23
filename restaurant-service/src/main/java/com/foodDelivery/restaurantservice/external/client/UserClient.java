package com.foodDelivery.restaurantservice.external.client;

import com.foodDelivery.restaurantservice.external.request.CartDetailsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @PostMapping("/cart")
    public String addFoodToCart(@RequestBody CartDetailsRequest cartDetailsRequest);
}
