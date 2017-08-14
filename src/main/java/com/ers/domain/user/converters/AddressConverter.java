package com.ers.domain.user.converters;

import com.ers.domain.user.Address;
import com.ers.domain.user.dto.AddressDto;

public class AddressConverter {

    public static AddressDto convertToDto(Address address) {
        return AddressDto.builder()
                .house(address.getHouse())
                .area(address.getArea())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address convertToAddress(AddressDto addressDto) {
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
