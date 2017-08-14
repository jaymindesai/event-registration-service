package com.ers.domain.event.converters;

import com.ers.domain.event.Venue;
import com.ers.domain.event.dto.VenueDto;

import static java.util.stream.Collectors.toList;

public class VenueConverter {

    public static VenueDto convertToDto(Venue venue) {
        return VenueDto.builder()
                .name(venue.getName())
                .city(venue.getCity())
                .timeSlots(venue.getTimeSlots().stream()
                        .map(TimeSlotConverter::convertToDto)
                        .collect(toList()))
                .build();
    }

    public static Venue convertToVenue(VenueDto venueDto) {
        Venue venue = Venue.builder()
                .name(venueDto.getName())
                .city(venueDto.getCity())
                .timeSlots(venueDto.getTimeSlots().stream()
                        .map(TimeSlotConverter::convertToTimeSlot)
                        .collect(toList()))
                .build();
        venue.getTimeSlots().forEach(slot -> slot.setVenue(venue));
        return venue;
    }
}
