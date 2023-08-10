package com.foodDelivery.orderservice.controller;

import com.foodDelivery.orderservice.dto.OrderResponseDto;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.dto.OrderRequestDto;
import com.foodDelivery.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantClient restaurantClient;

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        log.info("Order details: {}", order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderDetails(@PathVariable long orderId) {
        OrderResponseDto orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}