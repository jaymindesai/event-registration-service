package com.ers.domain.user.converters;

import com.ers.domain.user.User;
import com.ers.domain.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final AddressConverter addressConverter;
    private final ContactConverter contactConverter;

    @Autowired
    public UserConverter(AddressConverter addressConverter, ContactConverter contactConverter) {
        this.addressConverter = addressConverter;
        this.contactConverter = contactConverter;
    }

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactDetails(contactConverter.convertToDto(user.getContactDetails()))
                .addressDetails(addressConverter.convertToDto(user.getAddressDetails()))
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .contactDetails(contactConverter.convertToContact(userDto.getContactDetails()))
                .addressDetails(addressConverter.convertToAddress(userDto.getAddressDetails()))
                .build();
    }
}
