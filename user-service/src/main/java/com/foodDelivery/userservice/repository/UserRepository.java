package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.Role;
import com.foodDelivery.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    List<Users> findAllByRole(Role role);
    Users findByUserName(String userName);
}