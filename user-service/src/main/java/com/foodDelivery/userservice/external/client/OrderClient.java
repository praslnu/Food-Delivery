package com.foodDelivery.userservice.external.client;

import com.foodDelivery.userservice.external.request.OrderRequest;
import com.foodDelivery.userservice.external.response.OrderResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "ORDER-SERVICE/order")
public interface OrderClient{
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody OrderRequest orderRequest);

    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    @GetMapping("/past")
    public List<OrderResponse> getPastOrders();

    default String fallbackMethod(Throwable throwable) {
        return "Order Service is down";
    }
}
