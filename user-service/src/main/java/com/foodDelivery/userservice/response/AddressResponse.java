package com.foodDelivery.userservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse{
    private Long id;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private Long pinCode;
}
