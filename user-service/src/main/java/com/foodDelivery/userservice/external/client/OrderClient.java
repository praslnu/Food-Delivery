package com.foodDelivery.userservice.external.client;

import com.foodDelivery.userservice.external.request.OrderRequest;
import com.foodDelivery.userservice.external.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE/order")
public interface OrderClient{
    @PostMapping("/placeOrder")
    public void placeOrder(@RequestBody OrderRequest orderRequest);

    @GetMapping("/past")
    public List<OrderResponse> getPastOrders();
}
