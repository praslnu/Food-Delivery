package com.foodDelivery.userservice.mapper;

import com.foodDelivery.userservice.entity.Users;
import com.foodDelivery.userservice.request.UserLoginRequest;
import com.foodDelivery.userservice.request.UserRequest;
import com.foodDelivery.userservice.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{
    @Autowired
    private ModelMapper modelMapper;

    public UserRequest getUserRequest(Users user) {
        UserRequest userRequest = modelMapper.map(user, UserRequest.class);
        return userRequest;
    }

    public Users getUser(UserRequest userRequest) {
        Users user = modelMapper.map(userRequest, Users.class);
        return user;
    }

    public Users getUser(UserLoginRequest userLoginRequest) {
        Users user = modelMapper.map(userLoginRequest, Users.class);
        return user;
    }

    public UserResponse getUserResponse(Users user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;
    }
}
