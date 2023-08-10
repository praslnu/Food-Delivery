package com.foodDelivery.userservice.controller;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.exception.BadRequestException;
import com.foodDelivery.userservice.repository.RoleRepository;
import com.foodDelivery.userservice.request.RoleRequest;
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
    private RoleRepository roleRepository;
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody RoleRequest roleRequest) throws Exception
    {
        Role savedRole;
        if (roleRequest == null)
        {
            log.error("invalid role details");
            throw new BadRequestException("Invalid role");
        }
        else
        {
            try
            {
                Role role = Role.builder()
                        .role(roleRequest.getRole())
                        .build();
                savedRole = roleRepository.save(role);
                log.info("successfully saved role details");
            }
            catch (Exception e)
            {
                log.error("error while saving a role");
                throw new Exception("Error while saving a role");
            }
        }
        return ResponseEntity.ok(savedRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRoles()
    {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}