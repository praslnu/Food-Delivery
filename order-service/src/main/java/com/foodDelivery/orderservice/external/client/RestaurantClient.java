package com.foodDelivery.orderservice.external.client;

import com.foodDelivery.orderservice.external.response.Restaurant;
import com.foodDelivery.orderservice.response.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT-SERVICE/restaurant")
public interface RestaurantClient{
    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable("id") long restaurantId);
}