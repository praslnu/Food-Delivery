package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role getByRole(String role);
}