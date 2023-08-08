package com.foodDelivery.restaurantservice.service;

import com.foodDelivery.restaurantservice.dto.RestaurantDto;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import com.foodDelivery.restaurantservice.exception.NotFoundException;
import com.foodDelivery.restaurantservice.repository.RestaurantRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RestaurantService{
    @Autowired
    private RestaurantRepo restaurantRepo;
    public Long addRestaurant(RestaurantDto restaurantDto) {
        log.info("Adding Restaurant..");
        Restaurant restaurant
                = Restaurant.builder()
                .name(restaurantDto.getName())
                .build();
        restaurantRepo.save(restaurant);
        log.info("Restaurant Created");
        return restaurant.getId();
    }

    public Restaurant getRestaurantById(long restaurantId) {
        log.info("Get the Restaurant for id: {}", restaurantId);
        Restaurant restaurant
                = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s not found", restaurantId)));
        return restaurant;
    }
}