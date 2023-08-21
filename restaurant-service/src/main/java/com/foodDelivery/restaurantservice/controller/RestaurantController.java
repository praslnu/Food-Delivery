package com.foodDelivery.restaurantservice.controller;

import com.foodDelivery.restaurantservice.external.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
import com.foodDelivery.restaurantservice.request.ReviewRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import com.foodDelivery.restaurantservice.response.RestaurantResponse;
import com.foodDelivery.restaurantservice.response.ReviewResponse;
import com.foodDelivery.restaurantservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController{
    @Autowired
    private RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurants(Pageable pageable, @RequestParam(required = false) String name, @RequestParam(required = false) String foodName) {
        List<RestaurantResponse> restaurants;
        if (name != null && foodName != null) {
            restaurants = restaurantService.filterRestaurantsByNameAndFood(pageable, name, foodName);
        } else if (name != null) {
            restaurants = restaurantService.filterRestaurantsByName(pageable, name);
        } else if (foodName != null) {
            restaurants = restaurantService.filterRestaurantsByFood(pageable, foodName);
        } else {
            restaurants = restaurantService.getAllRestaurants(pageable);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<Long> addRestaurant(@RequestBody RestaurantRequest productRequest) {
        long restaurantId = restaurantService.addRestaurant(productRequest);
        return new ResponseEntity<>(restaurantId, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") long restaurantId) {
        RestaurantResponse restaurant = restaurantService.getRestaurantById(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping("/food/{id}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable("id") long foodId) {
        FoodResponse foodResponse = restaurantService.getFoodById(foodId);
        return new ResponseEntity<>(foodResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{restaurantId}/food/{foodId}")
    public ResponseEntity<RestaurantResponse> addFoodToRestaurant(@PathVariable long restaurantId, @PathVariable long foodId){
        return new ResponseEntity<>(restaurantService.addFoodToRestaurant(foodId, restaurantId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/cart")
    public ResponseEntity<String> addFoodToCart(Authentication authentication, @RequestBody CartDetailsRequest cartDetailsRequest){
        return new ResponseEntity<>(restaurantService.addFoodToCart(cartDetailsRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/review/{restaurantId}")
    public ReviewResponse addReview(Authentication authentication, @PathVariable long restaurantId, @RequestBody ReviewRequest reviewRequest){
        return restaurantService.addReview(authentication.getName(), restaurantId, reviewRequest);
    }
}