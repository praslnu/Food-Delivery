package com.foodDelivery.orderservice.request;

import com.foodDelivery.orderservice.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusRequest{
    private OrderStatus orderStatus;
}