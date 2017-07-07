package com.ers.domain.event.converters;

import com.ers.domain.event.TimeSlot;
import com.ers.domain.event.TimeSlotDto;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotConverter {

    public TimeSlotDto convertToDto(TimeSlot timeSlot){
        return TimeSlotDto.builder()
                .slotCode(timeSlot.getSlotCode())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .capacity(timeSlot.getCapacity())
                .build();
    }

    public TimeSlot convertToTimeSlot(TimeSlotDto timeSlotDto){
        return TimeSlot.builder()
                .slotCode(timeSlotDto.getSlotCode())
                .startTime(timeSlotDto.getStartTime())
                .endTime(timeSlotDto.getEndTime())
                .capacity(timeSlotDto.getCapacity())
                .build();
    }
}
