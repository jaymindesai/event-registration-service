package com.services.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(message = "First name must be defined")
    @Valid
    private String firstName;

    @NotNull(message = "Last name must be defined")
    @Valid
    private String lastName;

    @Email(message = "Please enter valid email address")
    @NotNull(message = "Email must be defined")
    @Valid
    private String email;

    @NotNull(message = "Contact details must be defined")
    @Valid
    private ContactDto contactDetails;

    @NotNull(message = "Address details must be defined")
    @Valid
    private AddressDto addressDetails;
}
