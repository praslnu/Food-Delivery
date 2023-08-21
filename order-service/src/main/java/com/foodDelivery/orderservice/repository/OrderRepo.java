package com.foodDelivery.orderservice.repository;

import com.foodDelivery.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    List<Order> findByOrderStatus(String orderStatus);

    @Query("SELECT o FROM Order o WHERE o.email = :email and o.restaurantId = :restaurantId and o.orderStatus = 'DELIVERED'")
    List<Order> findByEmailAndRestaurantId(String email, long restaurantId);
}
