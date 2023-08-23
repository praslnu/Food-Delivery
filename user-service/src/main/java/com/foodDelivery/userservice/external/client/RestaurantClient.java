package com.foodDelivery.userservice.external.client;

import com.foodDelivery.userservice.response.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT-SERVICE/restaurant")
public interface RestaurantClient{
    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable("id") Long restaurantId);
}
