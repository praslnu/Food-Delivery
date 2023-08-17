package com.foodDelivery.userservice.external.client;

import com.foodDelivery.userservice.external.request.OrderRequest;
import com.foodDelivery.userservice.external.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDER-SERVICE/order")
public interface OrderClient{
    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest);
}
