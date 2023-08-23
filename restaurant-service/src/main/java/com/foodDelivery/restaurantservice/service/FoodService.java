package com.foodDelivery.restaurantservice.service;

import com.foodDelivery.restaurantservice.mapper.FoodMapper;
import com.foodDelivery.restaurantservice.repository.FoodRepository;
import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.response.FoodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService{
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodMapper foodMapper;

    public FoodResponse createFood(FoodRequest foodRequest)
    {
        return foodMapper.getFood(foodRepository.save(foodMapper.getFood(foodRequest)));
    }

    public List<FoodResponse> getFoods()
    {
        return foodRepository.findAll().stream().map(food -> foodMapper.getFood(food)).collect(Collectors.toList());
    }
}