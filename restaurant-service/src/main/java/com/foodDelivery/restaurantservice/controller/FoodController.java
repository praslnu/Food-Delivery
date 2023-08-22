package com.foodDelivery.restaurantservice.controller;

import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import com.foodDelivery.restaurantservice.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController{
    @Autowired
    private FoodService foodService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/create")
    public ResponseEntity<FoodResponse> createFood(@RequestBody @Valid FoodRequest foodRequest){
        return ResponseEntity.ok(foodService.createFood(foodRequest));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<FoodResponse>> getFoods(){
        return ResponseEntity.ok(foodService.getFoods());
    }
}