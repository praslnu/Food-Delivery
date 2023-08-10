package com.foodDelivery.restaurantservice.controller;

import com.foodDelivery.restaurantservice.dto.RestaurantDto;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import com.foodDelivery.restaurantservice.response.RestaurantResponse;
import com.foodDelivery.restaurantservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController{
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody RestaurantDto productRequest) {
        long restaurantId = restaurantService.addRestaurant(productRequest);
        return new ResponseEntity<>(restaurantId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") long restaurantId) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}