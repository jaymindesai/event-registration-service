package com.services.domain.event.converters;

import com.services.domain.event.EventDto;
import com.services.domain.event.Event;
import org.springframework.stereotype.Component;

@Component
public class EventConverter {

    private final VenueConverter venueConverter;

    public EventConverter(VenueConverter venueConverter){
        this.venueConverter = venueConverter;
    }

    public EventDto convertToDto(Event event){
        return EventDto.builder()
                .name(event.getName())
                .date(event.getDate())
                .venue(venueConverter.convertToDto(event.getVenue()))
                .build();
    }

    public Event convertToEvent(EventDto eventDto){
        return Event.builder()
                .name(eventDto.getName())
                .date(eventDto.getDate())
                .venue(venueConverter.convertToVenue(eventDto.getVenue()))
                .build();
    }
}
