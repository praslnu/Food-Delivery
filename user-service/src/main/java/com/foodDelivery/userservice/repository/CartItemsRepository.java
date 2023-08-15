package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Cart;
import com.foodDelivery.userservice.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long>{
    List<CartItems> findAllByCart(Cart cart);
}