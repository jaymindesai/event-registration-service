package com.services.domain.event;

import com.services.domain.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;

import java.time.LocalTime;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class TimeSlot extends AbstractEntity {

    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;

    @Column(name = "CAPACITY", nullable = false)
    @Max(value = 50)
    private Integer capacity;

    @ManyToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "VENUE_ID", nullable = false)
    private Venue venue;
}