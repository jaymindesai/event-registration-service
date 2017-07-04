package com.services.domain.user.converters;

import com.services.domain.user.Address;
import com.services.domain.user.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDto convertToDto(Address address){
        return AddressDto.builder()
                .house(address.getHouse())
                .area(address.getArea())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public Address convertToAddress(AddressDto addressDto){
        return Address.builder()
                .house(addressDto.getHouse())
                .area(addressDto.getArea())
                .street(addressDto.getStreet())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .zipCode(addressDto.getZipCode())
                .build();
    }
}
