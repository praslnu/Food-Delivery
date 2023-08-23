package com.foodDelivery.userservice.mapper;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.request.RoleRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper{
    @Autowired
    private ModelMapper modelMapper;

    public Role getRole(RoleRequest roleRequest) {
        Role role = modelMapper.map(roleRequest, Role.class);
        return role;
    }
}