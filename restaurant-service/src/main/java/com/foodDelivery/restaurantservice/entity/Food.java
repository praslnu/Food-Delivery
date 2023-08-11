package com.foodDelivery.restaurantservice.entity;

import com.foodDelivery.restaurantservice.model.FoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String foodType;
    private double price;

    @ManyToMany(mappedBy = "menu")
    private List<Restaurant> restaurants = new ArrayList<>();
}