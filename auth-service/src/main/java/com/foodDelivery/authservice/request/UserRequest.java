package com.foodDelivery.authservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest{
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private long phoneNumber;
}
