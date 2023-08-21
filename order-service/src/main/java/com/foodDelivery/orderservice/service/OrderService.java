package com.foodDelivery.orderservice.service;

import com.foodDelivery.orderservice.dto.OrderStatus;
import com.foodDelivery.orderservice.entity.Order;
import com.foodDelivery.orderservice.entity.OrderInfo;
import com.foodDelivery.orderservice.exception.BadRequestException;
import com.foodDelivery.orderservice.exception.NotFoundException;
import com.foodDelivery.orderservice.external.client.PaymentClient;
import com.foodDelivery.orderservice.external.client.RestaurantClient;
import com.foodDelivery.orderservice.external.client.UserClient;
import com.foodDelivery.orderservice.external.request.PaymentRequest;
import com.foodDelivery.orderservice.repository.OrderInfoRepo;
import com.foodDelivery.orderservice.repository.OrderRepo;
import com.foodDelivery.orderservice.request.OrderRequest;
import com.foodDelivery.orderservice.response.FoodResponse;
import com.foodDelivery.orderservice.response.OrderResponse;
import com.foodDelivery.orderservice.response.PaymentResponse;
import com.foodDelivery.orderservice.response.RestaurantResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class OrderService{
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderInfoRepo orderInfoRepo;
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private RestaurantClient restaurantClient;
    @Autowired
    private UserClient userClient;

    public void removeCartItems(long restaurantId){

    }

    public Order placeOrder(String email, OrderRequest orderRequest) {
        log.info("Creating an Order");
        Order order = Order.builder()
                .amount(orderRequest.getTotalPrice())
                .orderStatus("CREATED")
                .restaurantId(orderRequest.getRestaurantId())
                .build();

        Order newOrder = orderRepo.save(order);
        orderRequest.getFoods().forEach(food -> {
            OrderInfo orderInfo = OrderInfo.builder()
                    .foodId(food)
                    .order(newOrder)
                    .build();
            orderInfoRepo.save(orderInfo);
        });

        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentDto
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalPrice())
                .build();

        String orderStatus = null;
        try {
            paymentClient.doPayment(paymentDto);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepo.save(order);
        log.info("Order Placed successfully with Order Id: {}", order.getId());
        userClient.removeCartItems(orderRequest.getRestaurantId());
        log.info(String.format("Discarded items from the cart for restaurant id: %s", orderRequest.getRestaurantId()));
        return order;
    }

    public List<OrderResponse> getIncomingOrders(){
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> orders = orderRepo.findByOrderStatus("PLACED");
        orders.forEach(order -> {
            orderResponses.add(getOrderDetails(order.getId()));
        });
        return orderResponses;
    }

    public OrderResponse manageOrder(long orderId, OrderStatus orderStatus){
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order not found for the order Id: %s", orderId)));
        if (order.getOrderStatus().equals("PAYMENT_FAILED")){
            throw new BadRequestException("Invalid Order id!, Payment issue");
        }
        order.setOrderStatus(String.valueOf(orderStatus));
        orderRepo.save(order);
        return getOrderDetails(orderId);
    }

    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for Order Id : {}", orderId);
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found for the order Id:" + orderId));

        log.info("Invoking Product service to fetch the product for id: {}", order.getId());
        RestaurantResponse restaurantResponse = restaurantClient.getRestaurantById(order.getRestaurantId());

        log.info("Getting Food information form the restaurant service");
        List<OrderInfo> orderInfos = orderInfoRepo.findAllByOrder(order);
        List<FoodResponse> foodResponses = new ArrayList<>();
        orderInfos.forEach(orderInfo -> {
            FoodResponse foodResponse = restaurantClient.getFoodById(orderInfo.getFoodId());
            foodResponses.add(foodResponse);
        });

        log.info("Getting payment information form the payment Service");
        PaymentResponse paymentResponse = paymentClient.getPaymentDetailsByOrderId(order.getId());
        OrderResponse orderResponseDto = OrderResponse.builder()
                .restaurantResponse(restaurantResponse)
                .paymentResponse(paymentResponse)
                .orderId(order.getId())
                .foods(foodResponses)
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .build();
        return orderResponseDto;
    }
}