package com.ers.domain.event.converters;

import com.ers.domain.event.Event;
import com.ers.domain.event.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {

    private final VenueConverter venueConverter;

    @Autowired
    public EventConverter(VenueConverter venueConverter) {
        this.venueConverter = venueConverter;
    }

    public EventDto convertToDto(Event event) {
        return EventDto.builder()
                .code(event.getCode())
                .name(event.getName())
                .date(event.getDate())
                .venue(venueConverter.convertToDto(event.getVenue()))
                .build();
    }

    public Event convertToEvent(EventDto eventDto) {
        return Event.builder()
                .code(eventDto.getCode())
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .venue(venueConverter.convertToVenue(eventDto.getVenue()))
                .build();
    }
}
