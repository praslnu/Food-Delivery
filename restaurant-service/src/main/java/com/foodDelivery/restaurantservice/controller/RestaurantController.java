package com.foodDelivery.restaurantservice.controller;

import com.foodDelivery.restaurantservice.external.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
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
    public ResponseEntity<Long> addRestaurant(@RequestBody RestaurantRequest productRequest) {
        long restaurantId = restaurantService.addRestaurant(productRequest);
        return new ResponseEntity<>(restaurantId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") long restaurantId) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{restaurantId}/food/{foodId}")
    public ResponseEntity<RestaurantResponse> addFoodToRestaurant(@PathVariable long restaurantId, @PathVariable long foodId){
        return new ResponseEntity<>(restaurantService.addFoodToRestaurant(foodId, restaurantId), HttpStatus.OK);
    }

    // add food to cart
    @PutMapping("/cart")
    public ResponseEntity<String> addFoodToRestaurant(@RequestBody CartDetailsRequest cartDetailsRequest){
        return new ResponseEntity<>(restaurantService.addFoodToCart(cartDetailsRequest), HttpStatus.OK);
    }
}