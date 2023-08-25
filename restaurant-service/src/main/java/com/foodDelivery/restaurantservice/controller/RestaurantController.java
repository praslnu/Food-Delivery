package com.foodDelivery.restaurantservice.controller;

import com.foodDelivery.restaurantservice.request.CartDetailsRequest;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
import com.foodDelivery.restaurantservice.request.ReviewRequest;
import com.foodDelivery.restaurantservice.response.*;
import com.foodDelivery.restaurantservice.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController{
    @Autowired
    private RestaurantService restaurantService;

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getRestaurants(Pageable pageable, @RequestParam(required = false) String name, @RequestParam(required = false) String foodName) {
        List<RestaurantResponse> restaurants;
        if (!Objects.isNull(name) && !Objects.isNull(foodName)) {
            restaurants = restaurantService.filterRestaurantsByNameAndFood(pageable, name, foodName);
        } else if (!Objects.isNull(foodName)) {
            restaurants = restaurantService.filterRestaurantsByName(pageable, name);
        } else if (!Objects.isNull(foodName)) {
            restaurants = restaurantService.filterRestaurantsByFood(pageable, foodName);
        } else {
            restaurants = restaurantService.getAllRestaurants(pageable);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<NewRestaurantResponse> addRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return new ResponseEntity<>(restaurantService.addRestaurant(restaurantRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin')")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") Long restaurantId) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteRestaurant(@PathVariable("id") long restaurantId) {
        restaurantService.deleteRestaurantId(restaurantId);
        SuccessResponse successResponse = SuccessResponse.builder()
                .message("Deleted Restaurant Successfully")
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/{restaurantId}/food/{foodId}")
    public ResponseEntity<RestaurantResponse> addFoodToRestaurant(@PathVariable long restaurantId, @PathVariable long foodId){
        return new ResponseEntity<>(restaurantService.addFoodToRestaurant(foodId, restaurantId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PutMapping("/cart")
    public ResponseEntity<SuccessResponse> addFoodToCart(@RequestBody @Valid CartDetailsRequest cartDetailsRequest){
        SuccessResponse successResponse = SuccessResponse.builder()
                .message(restaurantService.addFoodToCart(cartDetailsRequest))
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/review/{restaurantId}")
    public ReviewResponse addReview(Authentication authentication, @PathVariable long restaurantId, @RequestBody ReviewRequest reviewRequest){
        return restaurantService.addReview(authentication.getName(), restaurantId, reviewRequest);
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('admin') || hasAuthority('staff')")
    @GetMapping("/review/{restaurantId}")
    public List<ReviewResponse> getReview(@PathVariable long restaurantId){
        return restaurantService.getReviews(restaurantId);
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/favourites/{restaurantId}")
    public ResponseEntity<SuccessResponse> addToFavourites(@PathVariable long restaurantId){
        SuccessResponse successResponse = SuccessResponse.builder()
                .message(restaurantService.addToFavourites(restaurantId))
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}