package com.services.domain.event.converters;

import com.services.domain.event.Venue;
import com.services.domain.event.VenueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class VenueConverter {

    private final TimeSlotConverter timeSlotConverter;

    @Autowired
    public VenueConverter(TimeSlotConverter timeSlotConverter) {
        this.timeSlotConverter = timeSlotConverter;
    }

    public VenueDto convertToDto(Venue venue) {
        return VenueDto.builder()
                .name(venue.getName())
                .city(venue.getCity())
                .timeSlots(venue.getTimeSlots().stream()
                        .map(timeSlotConverter::convertToDto)
                        .collect(toList()))
                .build();
    }

    public Venue convertToVenue(VenueDto venueDto) {
        return Venue.builder()
                .name(venueDto.getName())
                .city(venueDto.getCity())
                .timeSlots(venueDto.getTimeSlots().stream()
                        .map(timeSlotConverter::convertToTimeSlot)
                        .collect(toList()))
                .build();
    }
}
