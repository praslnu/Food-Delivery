package com.foodDelivery.orderservice.repository;

import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoRepo extends JpaRepository<OrderInfo, Long>{
    List<OrderInfo> findAllByOrder(Order order);
}
