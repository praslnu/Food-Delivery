package com.foodDelivery.userservice.service;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.entity.Users;
import com.foodDelivery.userservice.exception.NotFoundException;
import com.foodDelivery.userservice.mapper.UserMapper;
import com.foodDelivery.userservice.repository.RoleRepository;
import com.foodDelivery.userservice.repository.UserRepository;
import com.foodDelivery.userservice.request.UserRequest;
import com.foodDelivery.userservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;

    public UserResponse registerAdmins(UserRequest userRequest, Boolean isAdmin)
    {
        Users user =  userMapper.getUser(userRequest);
        Role role;
        if (isAdmin){
            role = roleRepository.getByRole("admin");
        }
        else {
            role = roleRepository.getByRole("staff");
        }
        if (role == null){
            throw new NotFoundException("Role not found");
        }
        user.setRole(role);
        return userMapper.getUserResponse(userRepository.save(user));
    }

    public UserResponse registerUser(UserRequest userRequest)
    {
        Users user =  userMapper.getUser(userRequest);
        Role role = roleRepository.getByRole("user");
        if (role == null){
            throw new NotFoundException("Role not found");
        }
        user.setRole(role);
        return userMapper.getUserResponse(userRepository.save(user));
    }
}