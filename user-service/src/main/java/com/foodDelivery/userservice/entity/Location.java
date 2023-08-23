package com.foodDelivery.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String streetLine1;
    private String streetLine2;
    private String city;
    private Long pinCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    UserDetails userDetails;
}