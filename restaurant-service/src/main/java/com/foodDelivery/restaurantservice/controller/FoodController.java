package com.foodDelivery.restaurantservice.controller;

import brave.Response;
import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import com.foodDelivery.restaurantservice.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController{
    @Autowired
    private FoodService foodService;

    @PostMapping("/create")
    public ResponseEntity<FoodResponse> createFood(@RequestBody FoodRequest foodRequest){
        return ResponseEntity.ok(foodService.createFood(foodRequest));
    }

    @GetMapping
    public ResponseEntity<List<FoodResponse>> createFood(){
        return ResponseEntity.ok(foodService.getFoods());
    }
}