package com.foodDelivery.restaurantservice.repository;

import com.foodDelivery.restaurantservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>{
}