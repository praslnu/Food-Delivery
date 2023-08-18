package com.foodDelivery.userservice.response;

import com.foodDelivery.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials{
    private String userName;
    private String password;
    private Role role;
}