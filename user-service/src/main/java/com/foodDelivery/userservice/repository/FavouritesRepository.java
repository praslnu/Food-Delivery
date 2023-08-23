package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long>{
    Favourites findByRestaurantId(Long restaurantId);
}