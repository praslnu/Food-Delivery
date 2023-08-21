package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByEmail(String email);
}
