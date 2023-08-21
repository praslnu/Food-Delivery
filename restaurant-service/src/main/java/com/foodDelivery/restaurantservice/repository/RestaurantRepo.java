package com.foodDelivery.restaurantservice.repository;

import com.foodDelivery.restaurantservice.entity.Food;
import com.foodDelivery.restaurantservice.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>{
    Restaurant findByMenu(Food food);

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE %:name%")
    List<Restaurant> findByRestaurantName(String name, Pageable pageable);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.menu m WHERE LOWER(r.name) LIKE %:name% AND LOWER(m.name) LIKE %:foodName%")
    List<Restaurant> findByRestaurantNameAndFoodName(String name, String foodName, Pageable pageable);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.menu m WHERE LOWER(m.name) LIKE %:foodName%")
    List<Restaurant> findByFoodName(String foodName, Pageable pageable);
}