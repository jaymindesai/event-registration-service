package com.services.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    @NotNull(message = "Event name must be defined")
    @Valid
    private String name;

    @NotNull(message = "Event date must be defined")
    @Valid
    private LocalDate date;

    @NotNull(message = "Event must have a venue")
    @Valid
    private VenueDto venue;
}
