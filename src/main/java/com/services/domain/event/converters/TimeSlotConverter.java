package com.services.domain.event.converters;

import com.services.domain.event.TimeSlotDto;
import com.services.domain.event.TimeSlot;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotConverter {

    public TimeSlotDto convertToDto(TimeSlot timeSlot){
        return TimeSlotDto.builder()
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .capacity(timeSlot.getCapacity())
                .build();
    }

    public TimeSlot convertToTimeSlot(TimeSlotDto timeSlotDto){
        return TimeSlot.builder()
                .startTime(timeSlotDto.getStartTime())
                .endTime(timeSlotDto.getEndTime())
                .capacity(timeSlotDto.getCapacity())
                .build();
    }
}
