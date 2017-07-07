package com.ers.domain.registration.converters;

import com.ers.domain.event.converters.EventConverter;
import com.ers.domain.event.converters.TimeSlotConverter;
import com.ers.domain.registration.Registration;
import com.ers.domain.registration.RegistrationDto;
import com.ers.domain.user.converters.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationConverter {

    private final UserConverter userConverter;
    private final EventConverter eventConverter;
    private final TimeSlotConverter timeSlotConverter;

    @Autowired
    public RegistrationConverter(UserConverter userConverter, EventConverter eventConverter, TimeSlotConverter timeSlotConverter) {
        this.userConverter = userConverter;
        this.eventConverter = eventConverter;
        this.timeSlotConverter = timeSlotConverter;
    }

    public RegistrationDto convertToDto(Registration registration) {
        return RegistrationDto.builder()
                .registrantName(registration.getUser().getFirstName() + " " + registration.getUser().getLastName())
                .registrantContact(registration.getUser().getContactDetails().getPrimary())
                .eventName(registration.getEvent().getName())
                .eventCode(registration.getEvent().getCode())
                .eventVenue(registration.getEvent().getVenue().getName())
                .timeSlotCode(registration.getTimeSlot().getSlotCode())
                .timeSlotId(registration.getTimeSlot().getId())
                .build();
    }

    public Registration convertToRegistration(RegistrationDto registrationDto) {
        return Registration.builder()
                .user(userConverter.convertToUser(registrationDto.getUser()))
                .event(eventConverter.convertToEvent(registrationDto.getEvent()))
                .timeSlot(timeSlotConverter.convertToTimeSlot(registrationDto.getTimeSlot()))
                .build();
    }
}
