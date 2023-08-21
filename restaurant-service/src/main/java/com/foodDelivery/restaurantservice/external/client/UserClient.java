package com.foodDelivery.restaurantservice.external.client;

import com.foodDelivery.restaurantservice.external.request.CartDetailsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE/user")
public interface UserClient{
    @PutMapping("/cart")
    public String addFoodToCart(@RequestBody CartDetailsRequest cartDetailsRequest);
}
