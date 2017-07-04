package com.services.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

public enum Slot {

    MORNING_FIRST("mfirst", LocalTime.of(7, 30), LocalTime.of(9, 0)),
    MORNING_SECOND("msecond", LocalTime.of(9, 30), LocalTime.of(11, 0)),
    AFTERNOON_FIRST("afirst", LocalTime.of(13, 0), LocalTime.of(14, 30)),
    AFTERNOON_SECOND("asecond", LocalTime.of(15, 0), LocalTime.of(16, 30));

    @Getter
    private String code;
    @Getter
    private LocalTime startTime;
    @Getter
    private LocalTime endTime;

    Slot(String code, LocalTime startTime, LocalTime endTime){
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
