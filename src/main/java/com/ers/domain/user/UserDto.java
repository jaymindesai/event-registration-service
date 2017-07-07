package com.ers.domain.user;

import com.ers.domain.AbstractDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends AbstractDto {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Integer id;

    @NotNull(message = "First name must be defined")
    @Valid
    private String firstName;

    @NotNull(message = "Last name must be defined")
    @Valid
    private String lastName;

    @Pattern(regexp = EMAIL_PATTERN, message = "Enter valid email address")
    @Valid
    private String email;

    @NotNull(message = "Contact details must be defined")
    @Valid
    private ContactDto contactDetails;

    @NotNull(message = "Address details must be defined")
    @Valid
    private AddressDto addressDetails;
}
