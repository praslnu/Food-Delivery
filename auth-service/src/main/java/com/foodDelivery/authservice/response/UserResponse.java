package com.foodDelivery.authservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse{
    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
}
