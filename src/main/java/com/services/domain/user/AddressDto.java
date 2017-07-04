package com.services.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String house;

    @NotNull(message = "Street must be defined")
    @Valid
    private String street;

    private String area;

    @NotNull(message = "City must be defined")
    @Valid
    private String city;

    @NotNull(message = "State must be defined")
    @Valid
    private String state;

    @NotNull(message = "ZipCode must be defined")
    @Valid
    private String zipCode;
}
