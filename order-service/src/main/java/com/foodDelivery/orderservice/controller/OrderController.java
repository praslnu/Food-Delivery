package com.foodDelivery.orderservice.controller;

import com.foodDelivery.orderservice.request.OrderStatusRequest;
import com.foodDelivery.orderservice.request.ReviewRequest;
import com.foodDelivery.orderservice.response.OrderResponse;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.request.OrderRequest;
import com.foodDelivery.orderservice.response.ReviewResponse;
import com.foodDelivery.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private RestaurantClient restaurantClient;

    @GetMapping("/restaurantServiceFallBack")
    public String restaurantServiceFallback() {
        return "Order Service is down!";
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/placeOrder")
    public String placeOrder(Authentication authentication, @RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(authentication.getName(), orderRequest);
        return "Successfully place an order";
    }

    @PreAuthorize("hasAuthority('user') || hasAuthority('staff') || hasAuthority('admin')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('staff') || hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getIncomingOrders(){
        return new ResponseEntity<>(orderService.getOrders("PLACED"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('staff') || hasAuthority('admin')")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> manageOrder(@PathVariable long orderId, @RequestBody OrderStatusRequest orderStatus){
        return new ResponseEntity<>(orderService.manageOrder(orderId, orderStatus.getOrderStatus()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/past")
    public List<OrderResponse> getPastOrders(Authentication authentication){
        return orderService.getUserOrders(authentication.getName(), "PAYMENT_FAILED");
    }

    @PreAuthorize("hasAuthority('user')")
    @PostMapping("/review/{restaurantId}")
    public ResponseEntity<ReviewResponse> addReview(Authentication authentication, @PathVariable long restaurantId, @RequestBody @Valid ReviewRequest reviewRequest){
        return new ResponseEntity<>(orderService.addReview(authentication.getName(), restaurantId, reviewRequest), HttpStatus.OK);
    }
}