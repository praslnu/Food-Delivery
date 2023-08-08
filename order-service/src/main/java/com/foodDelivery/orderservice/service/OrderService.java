package com.foodDelivery.orderservice.service;

import com.foodDelivery.orderservice.dto.OrderDto;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.repository.OrderRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderService{

    @Autowired
    private OrderRepo orderRepo;
    public long placeOrder(OrderDto OrderDto) {
        log.info("Creating an Order");
        Order order = Order.builder()
                .amount(OrderDto.getTotalAmount())
                .orderStatus("CREATED")
                .restaurantId(OrderDto.getRestaurantId())
                .build();
        orderRepo.save(order);
        return order.getId();
    }
}
