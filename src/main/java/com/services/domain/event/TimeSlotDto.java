package com.services.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {

    @NotNull(message = "Start time must be defined")
    @Valid
    private LocalTime startTime;

    @NotNull(message = "End time must be defined")
    @Valid
    private LocalTime endTime;

    @NotNull(message = "Capacity must be defined")
    @Max(value = 50)
    @Valid
    private Integer capacity;
}
