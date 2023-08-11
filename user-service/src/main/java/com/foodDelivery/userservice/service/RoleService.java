package com.foodDelivery.userservice.service;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.mapper.RoleMapper;
import com.foodDelivery.userservice.repository.RoleRepository;
import com.foodDelivery.userservice.request.RoleRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleMapper roleMapper;
    public Role addRole(RoleRequest roleRequest) throws Exception {
        Role savedRole;
        try
        {
            savedRole = roleRepository.save(roleMapper.getRole(roleRequest));
            log.info("successfully saved role details");
        }
        catch (Exception e)
        {
            log.error("error while saving a role");
            throw new Exception("Error while saving a role");
        }
        return savedRole;
    }

    public List<Role> getRoles() throws Exception {
        Role savedRole;
        return roleRepository.findAll();
    }
}