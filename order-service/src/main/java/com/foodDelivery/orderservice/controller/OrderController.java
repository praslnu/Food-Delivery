package com.foodDelivery.orderservice.controller;

import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.dto.OrderDto;
import com.foodDelivery.orderservice.external.response.Restaurant;
import com.foodDelivery.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantClient restaurantClient;

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        log.info("Order details: {}", order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}