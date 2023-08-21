package com.foodDelivery.restaurantservice.repository;

import com.foodDelivery.restaurantservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long>{
}