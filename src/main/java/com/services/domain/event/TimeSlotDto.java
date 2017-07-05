package com.services.domain.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.domain.AbstractDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimeSlotDto extends AbstractDto {

    @NotNull(message = "Slot code must be defined")
    @Valid
    private String slotCode;

    @JsonFormat(pattern = "KK:mm")
    @NotNull(message = "Start time must be defined")
    @Valid
    private LocalTime startTime;

    @JsonFormat(pattern = "KK:mm")
    @NotNull(message = "End time must be defined")
    @Valid
    private LocalTime endTime;

    @NotNull(message = "Capacity must be defined")
    @Max(value = 50)
    @Valid
    private int capacity;
}
