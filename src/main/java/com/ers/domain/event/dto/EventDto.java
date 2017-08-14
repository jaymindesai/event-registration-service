package com.ers.domain.event.dto;

import com.ers.domain.AbstractDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EventDto extends AbstractDto {

    @NotNull(message = "Event code must be defined")
    @Valid
    private String code;

    @NotNull(message = "Event name must be defined")
    @Valid
    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Event date must be defined")
    @Valid
    private LocalDate date;

    @NotNull(message = "Event must have a venue")
    @Valid
    private VenueDto venue;
}
