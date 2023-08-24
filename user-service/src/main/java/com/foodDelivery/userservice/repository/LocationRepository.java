package com.foodDelivery.userservice.repository;

import com.foodDelivery.userservice.entity.UserDetails;
import com.foodDelivery.userservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    List<Location> findAllByUserDetails(UserDetails userDetails);
}
