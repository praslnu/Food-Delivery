package com.foodDelivery.userservice.mapper;

import com.foodDelivery.userservice.entity.Location;
import com.foodDelivery.userservice.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper{
    @Autowired
    private ModelMapper modelMapper;

    public AddressResponse getAddress(Location location) {
        AddressResponse addressResponse = modelMapper.map(location, AddressResponse.class);
        return addressResponse;
    }
}
