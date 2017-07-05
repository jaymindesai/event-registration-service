package com.services.domain.registration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.services.domain.AbstractDto;
import com.services.domain.event.EventDto;
import com.services.domain.event.TimeSlot;
import com.services.domain.event.TimeSlotDto;
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
    private String registrantName;

    @NotNull(message = "User must have a primary contact")
    @Valid
    private String registrantContact;

    @NotNull(message = "Event must be associated to this registration")
    @Valid
    private String eventName;

    @NotNull(message = "Event must have a valid event code")
    @Valid
    private String eventCode;

    @NotNull(message = "Event must have a venue")
    @Valid
    private String eventVenue;

    @NotNull(message = "Time slot must be associated with this registration")
    @Valid
    private int timeSlotId;

    @NotNull(message = "Time slot must have a valid event code")
    @Valid
    private String timeSlotCode;

    @JsonIgnore
    @NotNull(message = "User must be associated to this registration")
    @Valid
    private UserDto user;

    @JsonIgnore
    @NotNull(message = "Event must be associated to this registration")
    @Valid
    private EventDto event;

    @JsonIgnore
    @NotNull(message = "Time slot must be associated to this registration")
    @Valid
    private TimeSlotDto timeSlot;
}
