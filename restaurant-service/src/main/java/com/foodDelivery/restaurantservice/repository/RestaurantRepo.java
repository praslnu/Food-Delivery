package com.foodDelivery.restaurantservice.repository;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{
    Restaurant findByMenu(Food food);
}