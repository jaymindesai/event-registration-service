package com.services.domain.registration;

import com.services.domain.AbstractDto;
import com.services.domain.event.EventDto;
import com.services.domain.user.UserDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegistrationDto extends AbstractDto {

    @NotNull(message = "User must be associated to this registration")
    @Valid
    private UserDto user;

    @NotNull(message = "Event must be associated to this registration")
    @Valid
    private EventDto event;

}
