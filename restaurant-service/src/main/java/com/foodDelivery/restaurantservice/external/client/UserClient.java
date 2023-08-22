package com.foodDelivery.restaurantservice.external.client;

import com.foodDelivery.restaurantservice.request.CartDetailsRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE/user")
@Validated
public interface UserClient{
    @PutMapping("/cart")
    public String addFoodToCart(@RequestBody @Valid CartDetailsRequest cartDetailsRequest);
}