package com.ers.domain.registration.converters;

import com.ers.domain.event.converters.EventConverter;
import com.ers.domain.event.converters.TimeSlotConverter;
import com.ers.domain.registration.Registration;
import com.ers.domain.registration.dto.RegistrationDto;
import com.ers.domain.user.converters.UserConverter;

public class RegistrationConverter {

    public static RegistrationDto convertToDto(Registration registration) {
        return RegistrationDto.builder()
                .registrantName(registration.getUser().getFirstName() + " " + registration.getUser().getLastName())
                .registrantContact(registration.getUser().getContactDetails().getPrimary())
                .eventName(registration.getEvent().getName())
                .eventCode(registration.getEvent().getCode())
                .eventVenue(registration.getEvent().getVenue().getName() + ", " +registration.getEvent().getVenue().getCity())
                .timeSlotCode(registration.getTimeSlot().getSlotCode())
                .timeSlotId(registration.getTimeSlot().getId())
                .build();
    }

    public static Registration convertToRegistration(RegistrationDto registrationDto) {
        return Registration.builder()
                .user(UserConverter.convertToUser(registrationDto.getUser()))
                .event(EventConverter.convertToEvent(registrationDto.getEvent()))
                .timeSlot(TimeSlotConverter.convertToTimeSlot(registrationDto.getTimeSlot()))
                .build();
    }
}
