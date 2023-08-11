package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long>{

}