package com.services.domain.registration.converters;

import com.services.domain.event.converters.EventConverter;
import com.services.domain.registration.Registration;
import com.services.domain.registration.RegistrationDto;
import com.services.domain.user.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConverter {

    private final UserConverter userConverter;
    private final EventConverter eventConverter;

    @Autowired
    public RegistrationConverter(UserConverter userConverter, EventConverter eventConverter) {
        this.userConverter = userConverter;
        this.eventConverter = eventConverter;
    }

    public RegistrationDto convertToDto(Registration registration) {
        return RegistrationDto.builder()
                .user(userConverter.convertToDto(registration.getUser()))
                .event(eventConverter.convertToDto(registration.getEvent()))
                .build();
    }

    public Registration convertToRegistration(RegistrationDto registrationDto) {
        return Registration.builder()
                .user(userConverter.convertToUser(registrationDto.getUser()))
                .event(eventConverter.convertToEvent(registrationDto.getEvent()))
                .build();
    }
}
