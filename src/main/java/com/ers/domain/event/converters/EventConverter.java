package com.ers.domain.event.converters;

import com.ers.domain.event.Event;
import com.ers.domain.event.dto.EventDto;

public class EventConverter {

    public static EventDto convertToDto(Event event) {
        return EventDto.builder()
                .code(event.getCode())
                .name(event.getName())
                .date(event.getDate())
                .venue(VenueConverter.convertToDto(event.getVenue()))
                .build();
    }

    public static Event convertToEvent(EventDto eventDto) {
        return Event.builder()
                .code(eventDto.getCode())
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .venue(VenueConverter.convertToVenue(eventDto.getVenue()))
                .build();
    }
}
