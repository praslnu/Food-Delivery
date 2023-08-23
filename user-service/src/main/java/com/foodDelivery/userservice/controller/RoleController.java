package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.mapper.RoleMapper;
import com.foodDelivery.userservice.repository.RoleRepository;
import com.foodDelivery.userservice.request.RoleRequest;
import com.foodDelivery.userservice.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Log4j2
public class RoleController{
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody RoleRequest roleRequest) throws Exception
    {
        if (roleRequest == null)
        {
            log.error("invalid role details");
            throw new BadRequestException("Invalid role");
        }
        return ResponseEntity.ok(roleService.addRole(roleRequest));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() throws Exception {
        return ResponseEntity.ok(roleService.getRoles());
    }
}