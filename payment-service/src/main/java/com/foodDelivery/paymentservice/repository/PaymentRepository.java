package com.foodDelivery.paymentservice.repository;

import com.foodDelivery.paymentservice.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, Long>{

}