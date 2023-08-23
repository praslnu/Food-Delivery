package com.foodDelivery.restaurantservice.mapper;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper{
    @Autowired
    private ModelMapper modelMapper;

    public Food getFood(FoodRequest foodRequest) {
        Food food = modelMapper.map(foodRequest, Food.class);
        return food;
    }

    public FoodResponse getFood(Food food) {
        FoodResponse foodResponse = modelMapper.map(food, FoodResponse.class);
        return foodResponse;
    }
}
