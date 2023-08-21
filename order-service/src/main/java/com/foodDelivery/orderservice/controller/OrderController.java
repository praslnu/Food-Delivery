package com.foodDelivery.orderservice.controller;

import com.foodDelivery.orderservice.request.OrderStatusRequest;
import com.foodDelivery.orderservice.response.OrderResponse;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.request.OrderRequest;
import com.foodDelivery.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantClient restaurantClient;

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(Authentication authentication, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(authentication.getName(), orderRequest);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getIncomingOrders(){
        return new ResponseEntity<>(orderService.getIncomingOrders(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('staff')")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> manageOrder(@PathVariable long orderId, @RequestBody OrderStatusRequest orderStatus){
        return new ResponseEntity<>(orderService.manageOrder(orderId, orderStatus.getOrderStatus()), HttpStatus.OK);
    }
}