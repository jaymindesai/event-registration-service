package com.ers.domain.event.converters;

import com.ers.domain.event.TimeSlot;
import com.ers.domain.event.dto.TimeSlotDto;

public class TimeSlotConverter {

    public static TimeSlotDto convertToDto(TimeSlot timeSlot) {
        return TimeSlotDto.builder()
                .slotCode(timeSlot.getSlotCode())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .capacity(timeSlot.getCapacity())
                .build();
    }

    public static TimeSlot convertToTimeSlot(TimeSlotDto timeSlotDto) {
        return TimeSlot.builder()
                .slotCode(timeSlotDto.getSlotCode())
                .startTime(timeSlotDto.getStartTime())
                .endTime(timeSlotDto.getEndTime())
                .capacity(timeSlotDto.getCapacity())
                .build();
    }
}
