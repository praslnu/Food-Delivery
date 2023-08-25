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
import com.foodDelivery.orderservice.request.ReviewRequest;
import com.foodDelivery.orderservice.response.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class OrderService{
    private OrderRepo orderRepo;
    private OrderInfoRepo orderInfoRepo;
    private PaymentClient paymentClient;
    private RestaurantClient restaurantClient;
    private UserClient userClient;
    private RabbitTemplate template;

    public void placeOrder(String email, OrderRequest orderRequest) {
        log.info("Creating an Order");
        Order order = Order.builder()
                .amount(orderRequest.getTotalPrice())
                .orderStatus("CREATED")
                .restaurantId(orderRequest.getRestaurantId())
                .email(email)
                .addressId(orderRequest.getAddressId())
                .build();

        Order newOrder = orderRepo.save(order);
        log.info("getting food details");
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
                .orderId(newOrder.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalPrice())
                .build();

        String orderStatus = null;
        try {
            paymentClient.doPayment(paymentDto);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        }
        catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }
        newOrder.setOrderStatus(orderStatus);
        orderRepo.save(newOrder);
        log.info("Order Placed successfully with Order Id: {}", newOrder.getId());
        userClient.removeCartItems(orderRequest.getRestaurantId());
        log.info(String.format("Discarded items from the cart for restaurant id: %s", orderRequest.getRestaurantId()));
    }

    public List<OrderResponse> getOrders(String orderStatus){
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> orders = orderRepo.findByOrderStatus(orderStatus);
        orders.forEach(order -> {
            orderResponses.add(getOrderDetails(order.getId()));
        });
        return orderResponses;
    }

    public List<OrderResponse> getUserOrders(String email, String orderStatus){
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> orders = orderRepo.findByOrderStatusNot(orderStatus);
        orders.forEach(order -> {
            if (order.getEmail().equals(email)){
                orderResponses.add(getOrderDetails(order.getId()));
            }
        });
        return orderResponses;
    }

    public OrderResponse manageOrder(long orderId, OrderStatus orderStatus){
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order not found for the order Id: %s", orderId)));
        if (order.getOrderStatus().equals("PAYMENT_FAILED")){
            log.error("Invalid Order id!, Payment issue");
            throw new BadRequestException("Invalid Order id!, Payment issue");
        }
        order.setOrderStatus(String.valueOf(orderStatus));
        orderRepo.save(order);
        template.convertAndSend(System.getenv("EXCHANGE"), System.getenv("ROUTING_KEY"), String.format("Hi Customer, Your Order Has been %s", String.valueOf(orderStatus)));
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

    public ReviewResponse addReview(String email, long restaurantId, ReviewRequest reviewRequest){
        log.info(String.format("adding the review for the restaurant id : %s", restaurantId));
        List<Order> orders = orderRepo.findByEmailAndRestaurantId(email, restaurantId);
        if (orders.size() == 0){
            log.error(String.format("Restaurant not found for id : %s", restaurantId));
            throw new BadRequestException(String.format("You have not ordered anything from the restaurant id : %s", restaurantId));
        }
        return restaurantClient.addReview(restaurantId, reviewRequest);
    }
}