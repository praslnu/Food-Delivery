package com.foodDelivery.orderservice.service;

import com.foodDelivery.orderservice.dto.OrderDto;
import com.foodDelivery.orderservice.dto.PaymentDto;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.external.client.PaymentClient;
import com.foodDelivery.orderservice.repository.OrderRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private PaymentClient paymentClient;
    public Order placeOrder(OrderDto OrderDto) {
        log.info("Creating an Order");
        Order order = Order.builder()
                .amount(OrderDto.getTotalAmount())
                .orderStatus("CREATED")
                .restaurantId(OrderDto.getRestaurantId())
                .build();
        order = orderRepo.save(order);

        log.info("Calling Payment Service to complete the payment");
        PaymentDto paymentDto
                = PaymentDto.builder()
                .orderId(order.getId())
                .paymentMode(OrderDto.getPaymentMode())
                .amount(OrderDto.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentClient.doPayment(paymentDto);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepo.save(order);

        log.info("Order Placed successfully with Order Id: {}", order.getId());
        return order;
    }
}