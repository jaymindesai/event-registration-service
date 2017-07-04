package com.services.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {

    @NotNull(message = "Venue name must be defined")
    @Valid
    private String name;

    @NotNull(message = "Venue city must be defined")
    @Valid
    private String city;

    @NotNull(message = "Venue must have time slots")
    @Valid
    private List<TimeSlotDto> timeSlots = new ArrayList<>();
}
