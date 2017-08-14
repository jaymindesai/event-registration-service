package com.ers.domain.user.dto;

import com.ers.domain.AbstractDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AddressDto extends AbstractDto {

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
