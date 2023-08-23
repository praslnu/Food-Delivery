package com.foodDelivery.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String email;

    @OneToMany(mappedBy = "userDetails", fetch = FetchType.EAGER)
    List<Location> locations;

    @OneToMany(mappedBy = "userDetails", fetch = FetchType.EAGER)
    List<Favourites> favourites;
}
