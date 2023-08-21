package com.foodDelivery.orderservice.external.client;

import com.foodDelivery.orderservice.request.ReviewRequest;
import com.foodDelivery.orderservice.response.FoodResponse;
import com.foodDelivery.orderservice.response.RestaurantResponse;
import com.foodDelivery.orderservice.response.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RESTAURANT-SERVICE/restaurant")
public interface RestaurantClient{
    @GetMapping("/{id}")
    public RestaurantResponse getRestaurantById(@PathVariable("id") long restaurantId);

    @GetMapping("/food/{id}")
    public FoodResponse getFoodById(@PathVariable("id") long foodId);

    @PostMapping("/review/{restaurantId}")
    public ReviewResponse addReview(@PathVariable long restaurantId, @RequestBody ReviewRequest reviewRequest);
}