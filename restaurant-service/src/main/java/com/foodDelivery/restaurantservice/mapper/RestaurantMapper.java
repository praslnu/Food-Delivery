package com.foodDelivery.restaurantservice.mapper;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import com.foodDelivery.restaurantservice.request.FoodRequest;
import com.foodDelivery.restaurantservice.request.RestaurantRequest;
import com.foodDelivery.restaurantservice.response.RestaurantResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper{
    @Autowired
    private ModelMapper modelMapper;

    public RestaurantResponse getRestaurant(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = modelMapper.map(restaurant, RestaurantResponse.class);
        return restaurantResponse;
    }

    public Restaurant getRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = modelMapper.map(restaurantRequest, Restaurant.class);
        return restaurant;
    }
}
