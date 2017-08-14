package com.ers.domain.event.dto;

import com.ers.domain.AbstractDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VenueDto extends AbstractDto {

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
