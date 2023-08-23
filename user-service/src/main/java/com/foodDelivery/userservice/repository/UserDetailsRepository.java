package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{
    UserDetails findByEmail(String email);
}
