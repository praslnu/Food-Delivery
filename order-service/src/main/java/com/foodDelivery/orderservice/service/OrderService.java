package com.foodDelivery.orderservice.service;

import com.foodDelivery.orderservice.dto.*;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.exception.NotFoundException;
import com.foodDelivery.orderservice.external.client.PaymentClient;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.external.request.PaymentRequest;
import com.foodDelivery.orderservice.repository.OrderRepo;
import com.foodDelivery.orderservice.request.OrderRequest;
import com.foodDelivery.orderservice.response.PaymentResponse;
import com.foodDelivery.orderservice.response.RestaurantResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class OrderService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private RestaurantClient restaurantClient;

    public Order placeOrder(OrderRequest orderRequest) {
        log.info("Creating an Order");
        Order order = Order.builder()
                .amount(orderRequest.getTotalPrice())
                .orderStatus("CREATED")
                .restaurant(orderRequest.getRestaurantId())
                .build();
        order = orderRepo.save(order);

//        log.info("Calling Payment Service to complete the payment");
//        PaymentRequest paymentDto
//                = PaymentRequest.builder()
//                .orderId(order.getId())
//                .paymentMode(orderRequest.getPaymentMode())
//                .amount(orderRequest.getTotalAmount())
//                .build();
//
//        String orderStatus = null;
//        try {
//            paymentClient.doPayment(paymentDto);
//            log.info("Payment done Successfully. Changing the Oder status to PLACED");
//            orderStatus = "PLACED";
//        } catch (Exception e) {
//            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
//            orderStatus = "PAYMENT_FAILED";
//        }
//        order.setOrderStatus(orderStatus);
//        orderRepo.save(order);
//        log.info("Order Placed successfully with Order Id: {}", order.getId());
        return order;
    }

    public OrderResponseDto getOrderDetails(long orderId) {
        log.info("Get order details for Order Id : {}", orderId);

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found for the order Id:" + orderId));

        log.info("Invoking Product service to fetch the product for id: {}", order.getId());
//        RestaurantResponse restaurantResponse = restaurantClient.getRestaurantById(order.getRestaurantId());
        log.info("Getting payment information form the payment Service");
        PaymentResponse paymentResponse = paymentClient.getPaymentDetailsByOrderId(order.getId());
        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
//                .restaurantResponse(restaurantResponse)
                .paymentResponse(paymentResponse)
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .build();
        return orderResponseDto;
    }
}