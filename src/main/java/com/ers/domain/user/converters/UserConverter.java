package com.ers.domain.user.converters;

import com.ers.domain.user.User;
import com.ers.domain.user.dto.UserDto;

public class UserConverter {

    public static UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .contactDetails(ContactConverter.convertToDto(user.getContactDetails()))
                .addressDetails(AddressConverter.convertToDto(user.getAddressDetails()))
                .build();
    }

    public static User convertToUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .contactDetails(ContactConverter.convertToContact(userDto.getContactDetails()))
                .addressDetails(AddressConverter.convertToAddress(userDto.getAddressDetails()))
                .build();
    }
}
